package com.shopmind.authcore.service;

public interface AuthorizationService {
    /**
     * 为 登录/注册 的用户发送验证码，同时返回验证码令牌
     * @param phoneNumber 手机号
     * @return verify token
     */
    String sendShorMsgForLoginOrRegister(String phoneNumber);

//    void loginAndRegister(String phoneNumber, );
}
