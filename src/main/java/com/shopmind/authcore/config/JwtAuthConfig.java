package com.shopmind.authcore.config;

import com.shopmind.authcore.properties.AuthProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Description: 认证相关的配置
 * Author: huangcy
 * Date: 2025-12-17
 */
@Configuration
@EnableConfigurationProperties(AuthProperties.class)
public class JwtAuthConfig {
}
