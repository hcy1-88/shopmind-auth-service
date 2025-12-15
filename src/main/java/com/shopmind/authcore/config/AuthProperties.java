package com.shopmind.authcore.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 认证服务配置属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "shopmind.auth")
public class AuthProperties {

    /**
     * JWT 公钥
     */
    private String jwtPublicKey;

    /**
     * JWT 私钥
     */
    private String jwtPrivateKey;

    /**
     * Token 过期时间（毫秒）
     * 默认 7 天
     */
    private Long tokenExpirationMs = 7 * 24 * 60 * 60 * 1000L;

    /**
     * Refresh Token 过期时间（毫秒）
     * 默认 30 天
     */
    private Long refreshTokenExpirationMs = 30 * 24 * 60 * 60 * 1000L;
}
