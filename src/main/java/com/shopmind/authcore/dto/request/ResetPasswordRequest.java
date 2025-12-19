package com.shopmind.authcore.dto.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String newPassword;

    public ResetPasswordRequest(String newPassword) {
        this.newPassword = newPassword;
    }
}