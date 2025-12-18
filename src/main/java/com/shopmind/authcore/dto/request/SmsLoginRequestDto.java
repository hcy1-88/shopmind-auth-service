package com.shopmind.authcore.dto.request;

import lombok.Data;

/**
 * Description:
 * Author: huangcy
 * Date: 2025-12-18
 */
@Data
public class SmsLoginRequestDto {
    private String phoneNumber;
    private String code;
    private String token;
}
