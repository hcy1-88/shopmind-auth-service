package com.shopmind.authcore.dto;

import lombok.Data;

@Data
public class Captcha {

    /**
     * 标识当前请求验证码的唯一 id
     **/
    private String nonceStr;
    /**
     * 验证值
     **/
    private String value;
    /**
     * 生成的画布的base64，作为背景图（已经被扣掉一块）
     **/
    private String canvasSrc;
    /**
     * 画布宽度
     **/
    private Integer canvasWidth;
    /**
     * 画布高度
     **/
    private Integer canvasHeight;
    /**
     * 生成的阻塞块的base64，作为滑块的抠图
     **/
    private String blockSrc;
    /**
     * 阻塞块宽度
     **/
    private Integer blockWidth;
    /**
     * 阻塞块高度
     **/
    private Integer blockHeight;
    /**
     * 阻塞块凸凹半径
     **/
    private Integer blockRadius;
    /**
     * 阻塞块的横轴坐标
     **/
    private Integer blockX;
    /**
     * 阻塞块的纵轴坐标
     **/
    private Integer blockY;

}
