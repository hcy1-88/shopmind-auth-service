package com.shopmind.authcore.dto.request;

import lombok.Data;

/**
 * Description:
 * Author: huangcy
 * Date: 2025-12-15
 */
@Data
public class VerifyCaptchaRequestDto {
    private String imageKey;
    private String blockX;
}
