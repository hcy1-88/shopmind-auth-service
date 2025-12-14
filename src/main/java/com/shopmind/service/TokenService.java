package com.shopmind.service;

import com.shopmind.dto.Users;

public interface TokenService {
    String generateAccessToken(Users user);
}
