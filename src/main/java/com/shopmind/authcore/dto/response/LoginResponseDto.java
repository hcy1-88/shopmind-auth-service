package com.shopmind.authcore.dto.response;

import lombok.Data;

/**
 * Description:
 * Author: huangcy
 * Date: 2025-12-18
 */
@Data
public class LoginResponseDto {
    private UserResponseDto user;
    private String token;
}
