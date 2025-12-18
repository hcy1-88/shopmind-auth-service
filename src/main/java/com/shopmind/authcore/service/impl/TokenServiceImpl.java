package com.shopmind.authcore.service.impl;

import com.shopmind.authcore.exception.AuthServiceException;
import com.shopmind.authcore.properties.AuthProperties;
import com.shopmind.authcore.dto.response.UserResponseDto;
import com.shopmind.authcore.properties.SMSProperties;
import com.shopmind.authcore.service.TokenService;
import com.shopmind.framework.exception.ShopmindException;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.shopmind.framework.util.JwtUtils.loadPrivateKeyFromBase64;

/**
 * Token 生成服务
 * 负责根据用户信息生成 JWT Token
 */
@Slf4j
@Service
public class TokenServiceImpl implements TokenService {

    @Resource
    private AuthProperties authProperties;

    @Resource
    private SMSProperties smsProperties;

    /**
     * 私钥（用于签名 Token）
     */
    private PrivateKey privateKey;

    /**
     * 初始化私钥
     */
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        try {
            String privateKey = authProperties.getJwtPrivateKey();
            if (privateKey == null || privateKey.isEmpty()) {
                log.error("JWT 私钥未配置，请在 application.yml 中配置 shopmind.auth.jwt-private-key");
                throw new IllegalStateException("JWT 私钥未配置");
            }

            this.privateKey = loadPrivateKeyFromBase64(privateKey);
            log.info("Token 服务初始化成功");
        } catch (Exception e) {
            log.error("初始化 Token 服务失败", e);
            throw new RuntimeException("初始化 Token 服务失败", e);
        }
    }

    /**
     * 为用户生成访问令牌（Access Token）
     *
     * @param user 用户信息
     * @return JWT Token
     */
    @Override
    public String generateAccessToken(UserResponseDto user) {
        return generateAccessToken(user, authProperties.getTokenExpirationMs());
    }

    @Override
    public String generateVerificationCodeToken(String phoneNumber, String code) {
        try {
            Date now = new Date();
            Date expiration = new Date(now.getTime() + smsProperties.getExpire() * 60 * 1000);
            Map<String, Object> claims = new HashMap<>();
            claims.put("phoneNumber", phoneNumber);
            claims.put("code", code);
            return Jwts.builder()
                    .subject(phoneNumber)
                    .claims(claims)
                    .issuedAt(now)
                    .expiration(expiration)
                    .signWith(privateKey)
                    .compact();
        } catch (Exception exception){
            log.error("生成验证码令牌失败：{}", exception.getMessage(),  exception);
            throw new ShopmindException("验证码令牌生成失败！", exception);
        }
    }

    /**
     * 为用户生成访问令牌（Access Token，带过期时间）
     *
     * @param user         用户信息
     * @param expirationMs 过期时间（毫秒）
     * @return JWT Token
     */
    public String generateAccessToken(UserResponseDto user, long expirationMs) {
        try {
            Date now = new Date();
            Date expiration = new Date(now.getTime() + expirationMs);

            // 构建 JWT Claims
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", user.getId());
            claims.put("phone", user.getPhone());
            claims.put("nickname", user.getNickname());
            claims.put("avatar", user.getAvatar());
            claims.put("gender", user.getGender());
            claims.put("age", user.getAge());
            claims.put("type", "access_token");

            // 生成 Token（使用私钥签名）
            String token = Jwts.builder()
                    .subject(String.valueOf(user.getId()))
                    .claims(claims)
                    .issuedAt(now)
                    .expiration(expiration)
                    .signWith(privateKey)
                    .compact();

            log.info("为用户生成访问令牌成功 - userId: {}, nickname: {}", user.getId(), user.getNickname());
            return token;
        } catch (Exception e) {
            log.error("生成访问令牌失败", e);
            throw new AuthServiceException("AUTH0005", e);
        }
    }

}