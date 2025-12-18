package com.shopmind.authcore.config;

import com.shopmind.authcore.properties.AuthProperties;
import com.shopmind.framework.provider.PublicKeyProvider;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description: 认证相关的配置
 * Author: huangcy
 * Date: 2025-12-17
 */
@Configuration
@EnableConfigurationProperties(AuthProperties.class)
public class JwtAuthConfig {
    @Resource
    private AuthProperties authProperties;

    @Bean
    public PublicKeyProvider publicKeyProvider() {
        return new PublicKeyProvider(authProperties.getJwtPublicKey());
    }
}
