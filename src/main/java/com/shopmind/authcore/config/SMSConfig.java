package com.shopmind.authcore.config;

import com.aliyun.dypnsapi20170525.Client;
import com.shopmind.authcore.properties.SMSProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.aliyun.teaopenapi.models.Config;

/**
 * Description: 短信认证服务配置类
 * Author: huangcy
 * Date: 2025-12-17
 */
@EnableConfigurationProperties(SMSProperties.class)
@Configuration
public class SMSConfig {
    @Bean
    public Client smsClient(SMSProperties smsProperties) throws Exception {
        Config config = new Config()
                .setAccessKeyId(smsProperties.getAccessKeyId())
                .setAccessKeySecret(smsProperties.getAccessKeySecret());
        config.endpoint = smsProperties.getEndpoint();
        return new Client(config);
    }
}
