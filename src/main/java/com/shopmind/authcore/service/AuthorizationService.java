package com.shopmind.authcore.service;

import com.shopmind.authcore.dto.response.LoginResponseDto;
import com.shopmind.authcore.dto.response.UserResponseDto;
import com.shopmind.framework.context.ResultContext;

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

    /**
     * 手机号 + 密码 登录
     * @param phoneNumber 手机号
     * @param password 密码
     * @return token 和 user
     */
    ResultContext<LoginResponseDto> loginByPwd(String phoneNumber, String password);

    /**
     * 初次设置密码
     * @param phoneNumber 手机号
     * @param smsCode 短信验证码
     * @param smsToken 验证码 token
     * @param password 密码
     * @param confirmPassword 确认密码
     */
    void setPasswordFirstTime(String phoneNumber, String smsCode, String smsToken, String password, String confirmPassword);

    /**
     * 重置密码
     * @param password 密码
     * @param confirmPassword 确认密码
     */
    void resetPassword(String password, String confirmPassword);

}
