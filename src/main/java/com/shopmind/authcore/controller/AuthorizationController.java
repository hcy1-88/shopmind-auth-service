package com.shopmind.authcore.controller;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.shopmind.authcore.dto.SmsCodeRequestDto;
import com.shopmind.authcore.exception.AuthServiceException;
import com.shopmind.authcore.service.AuthorizationService;
import com.shopmind.framework.context.ResultContext;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Description: 认证
 * Author: huangcy
 * Date: 2025-12-17
 */
@RestController
@RequestMapping("/authorization")
public class AuthorizationController {
    @Resource
    private AuthorizationService authorizationService;

    @PostMapping("/send-sms-code")
    public ResultContext<Map<String, Object>> sendSmsCode(@RequestBody SmsCodeRequestDto smsCodeRequestDto) {
        String phoneNumber = smsCodeRequestDto.getPhoneNumber();
        if (StringUtils.isEmpty(phoneNumber)) {
            throw new AuthServiceException("AUTH0006");
        }
        String token = authorizationService.sendShorMsgForLoginOrRegister(phoneNumber);
        return ResultContext.success(Map.of("token", token));
    }

    // todo 短信验证码登录
//    @PostMapping("/sms-login")
//    public ResultContext<> smsLogin(){
//
//    }
}
