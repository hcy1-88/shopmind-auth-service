package com.shopmind.authcore.service;

import com.shopmind.authcore.dto.Users;

public interface TokenService {
    String generateAccessToken(Users user);
}
