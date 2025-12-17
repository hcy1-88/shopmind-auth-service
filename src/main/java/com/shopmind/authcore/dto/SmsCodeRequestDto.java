package com.shopmind.authcore.dto;

import lombok.Data;

/**
 * Description: 请求验证码
 * Author: huangcy
 * Date: 2025-12-17
 */
@Data
public class SmsCodeRequestDto {
    private String phoneNumber;
}
