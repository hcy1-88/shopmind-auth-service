package com.shopmind.authcore.config;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description: 服务相关的配置类
 * Author: huangcy
 * Date: 2025-12-17
 */
@Configuration
public class AuthServiceConfig {
    @Bean
    public Gson gson() {
        return new Gson();
    }
}
