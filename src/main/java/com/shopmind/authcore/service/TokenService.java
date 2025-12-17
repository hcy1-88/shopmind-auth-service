package com.shopmind.authcore.service;

import com.shopmind.authcore.dto.Users;

public interface TokenService {

    /**
     * 生成验证码的令牌
     * @param phoneNumber 手机号
     * @param code 验证码
     * @return token
     */
    String generateVerificationCodeToken(String phoneNumber, String code);

    /**
     * 用户登录超过后，获取 token 令牌
     * @param user 用户信息
     * @return 访问令牌
     */
    String generateAccessToken(Users user);
}
