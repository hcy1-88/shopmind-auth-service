package com.shopmind.authcore.dto.request;

import lombok.Data;

/**
 * Description: 密码登录请求参数
 * Author: huangcy
 * Date: 2025-12-18
 */
@Data
public class PasswordLoginDto {
    private String phoneNumber;
    private String password;
}
