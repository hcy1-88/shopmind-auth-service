package com.shopmind.authcore.service;

import com.shopmind.authcore.dto.response.LoginResponseDto;
import com.shopmind.authcore.dto.response.UserResponseDto;

public interface AuthorizationService {
    /**
     * 为 登录/注册 的用户发送验证码，同时返回验证码令牌
     * @param phoneNumber 手机号
     * @return verify token
     */
    String sendShorMsgForLoginOrRegister(String phoneNumber);

    /**
     * 短信登录/注册
     * @param phoneNumber 手机号
     * @param code 手机短信验证码
     * @param smsToken 令牌
     */
    LoginResponseDto smsLoginAndRegister(String phoneNumber, String code, String smsToken);

    /**
     * 以 token 获取当前用户信息
     * @param token 访问令牌
     * @return 最新用户信息
     */
    UserResponseDto getMeInfo(String token);

}
