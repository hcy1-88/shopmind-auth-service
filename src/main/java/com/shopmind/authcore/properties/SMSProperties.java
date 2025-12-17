package com.shopmind.authcore.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: 短信认证服务-配置
 * Author: huangcy
 * Date: 2025-12-17
 */
@Data
@ConfigurationProperties(prefix = "shopmind.sms")
public class SMSProperties {
    private String accessKeyId;
    private String accessKeySecret;
    private String endpoint = "dypnsapi.aliyuncs.com";
    private String signName = "速通互联验证码";


    /**
     * 用 Map 存储模板 code，key 对应枚举的 codeKey
     */
    private Map<String, String> templateCodes = new HashMap<>();

    /**
     * 短信有效时长，单位 分钟
     */
    private Integer expire;
}
