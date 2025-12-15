package com.shopmind.authcore.dto;

import lombok.Data;

/**
 * Description:
 * Author: huangcy
 * Date: 2025-12-15
 */
@Data
public class VerifyCaptchaDto {
    private String imageKey;
    private String imageCode;
}
