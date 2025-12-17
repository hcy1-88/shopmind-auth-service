package com.shopmind.authcore.service.impl;

import com.aliyun.dypnsapi20170525.Client;
import com.aliyun.dypnsapi20170525.models.SendSmsVerifyCodeRequest;
import com.aliyun.dypnsapi20170525.models.SendSmsVerifyCodeResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teautil.models.RuntimeOptions;
import com.google.gson.Gson;
import com.shopmind.authcore.enums.SMSTemplateEnum;
import com.shopmind.authcore.exception.AuthServiceException;
import com.shopmind.authcore.properties.SMSProperties;
import com.shopmind.authcore.service.SMSService;
import com.shopmind.framework.exception.ShopmindException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Description: 短信验证码
 * Author: huangcy
 * Date: 2025-12-17
 */
@Slf4j
@Service
public class SMSServiceImpl implements SMSService {
    @Resource
    private SMSProperties smsProperties;

    @Resource
    private Client client;

    @Resource
    private Gson gson;

    @Override
    public void sendShortMessage(String phoneNumber, String smsCode, SMSTemplateEnum smsTemplateEnum) {
        try {
            String templateCode = smsProperties.getTemplateCodes()
                    .get(smsTemplateEnum.getCodeKey());
            if (templateCode == null) {
                throw new ShopmindException("未配置短信模板: " + smsTemplateEnum.getDescription());
            }
            String param = String.format("{\"code\":\"%s\",\"min\":\"%d\"}", smsCode, smsProperties.getExpire());
            SendSmsVerifyCodeRequest request = new SendSmsVerifyCodeRequest()
                    .setSignName(smsProperties.getSignName())
                    .setTemplateCode(templateCode)
                    .setPhoneNumber(phoneNumber)
                    .setTemplateParam(param);
            RuntimeOptions runtime = new RuntimeOptions();
            SendSmsVerifyCodeResponse smsResponse = client.sendSmsVerifyCodeWithOptions(request, runtime);
            log.info("手机号：{}，发送结果：{}", phoneNumber, gson.toJson(smsResponse));
        } catch (TeaException error){
            log.error("平台发送短信失败：{}, 手机号：{}", error.getData().get("Recommend"), phoneNumber);
            throw new AuthServiceException("AUTH0003", error);
        }
        catch (Exception e) {
            log.error("发送短信方法出现异常：{}", e.getMessage(), e);
            throw new AuthServiceException("AUTH0004", e);
        }

    }
}
