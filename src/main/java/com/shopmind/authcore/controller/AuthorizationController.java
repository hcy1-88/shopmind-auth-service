package com.shopmind.authcore.controller;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.shopmind.authcore.dto.request.PasswordLoginDto;
import com.shopmind.authcore.dto.request.SetPasswordRequest;
import com.shopmind.authcore.dto.request.SmsCodeRequestDto;
import com.shopmind.authcore.dto.request.SmsLoginRequestDto;
import com.shopmind.authcore.dto.response.LoginResponseDto;
import com.shopmind.authcore.dto.response.UserResponseDto;
import com.shopmind.authcore.exception.AuthServiceException;
import com.shopmind.authcore.service.AuthorizationService;
import com.shopmind.authcore.utils.TokenUtils;
import com.shopmind.framework.context.ResultContext;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/sms-login")
    public ResultContext<LoginResponseDto> smsLogin(@RequestBody SmsLoginRequestDto smsLoginRequestDto) {
        if (StringUtils.isEmpty(smsLoginRequestDto.getToken())) {
            throw new AuthServiceException("AUTH0007");
        }
        LoginResponseDto loginResponseDto = authorizationService.smsLoginAndRegister(
                smsLoginRequestDto.getPhoneNumber(),
                smsLoginRequestDto.getCode(),
                smsLoginRequestDto.getToken());
        return ResultContext.success(loginResponseDto);
    }

    @PostMapping("/me")
    public ResultContext<UserResponseDto> me(@RequestHeader(value = "Authorization") String authHeader) {
        String token = TokenUtils.extractTokenFromHeader(authHeader);
        UserResponseDto meInfo = authorizationService.getMeInfo(token);
        return ResultContext.success(meInfo);
    }
    
    @PostMapping("/login")
    public ResultContext<LoginResponseDto> login(@RequestBody PasswordLoginDto passwordLoginDto){
        return authorizationService.loginByPwd(passwordLoginDto.getPhoneNumber(), passwordLoginDto.getPassword());
    }

    @PostMapping("/set-password")
    public ResultContext<?> setPassword(@RequestBody SetPasswordRequest request) {
        authorizationService.setPasswordFirstTime(
                request.getPhoneNumber(),
                request.getCode(),
                request.getToken(),
                request.getPassword(),
                request.getConfirmPassword());
        return ResultContext.success();
    }
}
