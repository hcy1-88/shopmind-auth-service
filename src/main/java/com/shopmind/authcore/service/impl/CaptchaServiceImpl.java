package com.shopmind.authcore.service.impl;

import com.shopmind.authcore.dto.Captcha;
import com.shopmind.authcore.exception.AuthServiceException;
import com.shopmind.framework.exception.ShopmindException;
import com.shopmind.framework.id.IdGenerator;
import com.shopmind.framework.context.ResultContext;
import com.shopmind.authcore.service.CaptchaService;
import com.shopmind.authcore.utils.CaptchaUtils;
import com.shopmind.framework.model.FileObject;
import com.shopmind.framework.service.StorageService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


/**
 * Description: 图片验证码
 * Author: huangcy
 * Date: 2025-12-14
 */
@Slf4j
@Service
public class CaptchaServiceImpl implements CaptchaService {
    /**
     * 拼图验证码允许偏差
     **/
    private static final Integer ALLOW_DEVIATION = 3;

    /**
     * 前缀，存储抠图横坐标，即正确答案
     */
    private static final String IMAGE_CODE_PREFIX = "verifyImageCode:";

    /**
     * captcha 文件存储前缀
     */
    private static final String CAPTCHA_CODE_PREFIX = "captcha/";

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private IdGenerator idGenerator;

    @Resource
    private StorageService storageService;

    /**
     * 校验验证码
     **/
    @Override
    public ResultContext<Boolean> checkImageCode(String imageKey, String blockX) {
        RBucket<String> bucket = redissonClient.getBucket(IMAGE_CODE_PREFIX + imageKey);
        if(!bucket.isExists()) {
            return ResultContext.fail("验证码已失效!" );
        }
        // 根据移动距离判断验证是否成功
        if (Math.abs(Integer.parseInt(bucket.get()) - Integer.parseInt(blockX)) > ALLOW_DEVIATION) {
            return ResultContext.fail("验证失败，请控制拼图对齐缺口");
        }
        return ResultContext.success();
    }
    /**
     * 缓存验证码，有效期15分钟
     **/
    public void saveImageCode(String key, String code) {
        RBucket<String> bucket = redissonClient.getBucket(IMAGE_CODE_PREFIX + key);
        bucket.set(code, Duration.ofMinutes(15));
    }

    /**
     * 获取验证码拼图（生成的抠图和带抠图阴影的大图及抠图坐标）
     **/
    @Override
    public Object getCaptcha(Captcha captcha) {
        // 参数校验
        CaptchaUtils.checkCaptcha(captcha);
        // 获取画布的宽高
        int canvasWidth = captcha.getCanvasWidth();
        int canvasHeight = captcha.getCanvasHeight();
        // 获取阻塞块的宽高/半径
        int blockWidth = captcha.getBlockWidth();
        int blockHeight = captcha.getBlockHeight();
        int blockRadius = captcha.getBlockRadius();
        // 1，获取资源图
        BufferedImage canvasImage = getCaptchaImageRandom();
        // 调整原图到指定大小
        canvasImage = CaptchaUtils.imageResize(canvasImage, canvasWidth, canvasHeight);
        // 随机生成阻塞块坐标
        int blockX = CaptchaUtils.getNonceByRange(blockWidth, canvasWidth - blockWidth - 10);
        int blockY = CaptchaUtils.getNonceByRange(10, canvasHeight - blockHeight + 1);
        // 阻塞块
        BufferedImage blockImage = new BufferedImage(blockWidth, blockHeight, BufferedImage.TYPE_4BYTE_ABGR);
        // 新建的图像根据轮廓图颜色赋值，源图生成遮罩
        CaptchaUtils.cutByTemplate(canvasImage, blockImage, blockWidth, blockHeight, blockRadius, blockX, blockY);
        // 2，生成唯一 id，存 redis 缓存正确答案
        String nonceStr = idGenerator.nextIdStr().replaceAll("-", "");
        saveImageCode(nonceStr,String.valueOf(blockX));
        // 3，返回参数：唯一 id（用于后续验证 x 坐标位置）、滑块高度、滑块图片、背景图片
        captcha.setNonceStr(nonceStr);
        captcha.setBlockY(blockY);
        captcha.setBlockSrc(CaptchaUtils.toBase64(blockImage, "png"));
        captcha.setCanvasSrc(CaptchaUtils.toBase64(canvasImage, "png"));
        return captcha;
    }

    @Override
    public BufferedImage getCaptchaImageRandom() {
        String url = "";
        try {
            List<FileObject> fileObjects = storageService.listFiles(CAPTCHA_CODE_PREFIX);
            if (fileObjects.isEmpty()) {
                throw new AuthServiceException("AUTH0001");
            }
            List<String> urls = fileObjects.stream().map(FileObject::getFileUrl).toList();
            url = urls.get(ThreadLocalRandom.current().nextInt(urls.size()));
            // 从URL读取图片
            return ImageIO.read(new URL(url).openStream());
        } catch (IOException e) {
            log.error("读取验证码图片失败: {}", url, e);
            throw new AuthServiceException("AUTH0002");
        }
    }

}
