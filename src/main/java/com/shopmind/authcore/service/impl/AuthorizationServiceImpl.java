package com.shopmind.authcore.service.impl;

import com.shopmind.authcore.enums.SMSTemplateEnum;
import com.shopmind.authcore.service.AuthorizationService;
import com.shopmind.authcore.service.SMSService;
import com.shopmind.authcore.service.TokenService;
import com.shopmind.authcore.utils.SMSUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Description: 认证服务
 * Author: huangcy
 * Date: 2025-12-17
 */
@Slf4j
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Resource
    private SMSService smsService;

    @Resource
    private TokenService tokenService;

    @Override
    public String sendShorMsgForLoginOrRegister(String phoneNumber) {
        // todo 开发阶段先固定验证码，不发短信
//        String code = SMSUtil.generateSMSCode();
        String code ="123456";
//        smsService.sendShortMessage(phoneNumber, code, SMSTemplateEnum.LOGIN_REGISTER);
        return tokenService.generateVerificationCodeToken(phoneNumber, code);
    }
}
