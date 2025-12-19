package com.shopmind.authcore.dto.request;

import lombok.Data;

/**
 * 设置密码时的请求体：
 *   - 如果用户未登录，说明用户第一次设置密码，此时参数是齐全的
 *   - 如果用户已登录，要改密码，那么 phoneNumber、code、token 是前端无需传递的
 */
@Data
public class SetPasswordRequest {
    String phoneNumber;
    /**
     * 验证码
     */
    String code;

    /**
     * 验证码 token
     */
    String token;

    /**
     * 密码
     */
    private String password;

    /**
     * 确认密码
     */
    private String confirmPassword;
}