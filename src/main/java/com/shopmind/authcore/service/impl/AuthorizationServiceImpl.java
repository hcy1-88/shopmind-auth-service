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
        String code = SMSUtil.generateSMSCode();
        smsService.sendShortMessage(phoneNumber, code, SMSTemplateEnum.LOGIN_REGISTER);
        return tokenService.generateVerificationCodeToken(phoneNumber, code);
    }
}
