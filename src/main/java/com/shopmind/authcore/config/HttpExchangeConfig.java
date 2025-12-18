package com.shopmind.authcore.config;

import com.shopmind.authcore.clients.UserServiceClient;
import com.shopmind.framework.constant.ServiceNameConstant;
import com.shopmind.framework.util.ShopmindHttpClientUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Description: HTTP Exchange 配置类
 * Author: huangcy
 * Date: 2025-12-18
 */
@Configuration
public class HttpExchangeConfig {

    /**
     * 创建 UserServiceClient 的代理实例
     * 注入框架提供的 @LoadBalanced RestClient.Builder（来自 HttpExchangeAutoConfig）
     */
    @Bean
    public UserServiceClient userServiceClient(RestClient.Builder builder) {
        return ShopmindHttpClientUtils.createLoadBalancedClient(builder, ServiceNameConstant.USER_SERVICE, UserServiceClient.class);
    }
}
