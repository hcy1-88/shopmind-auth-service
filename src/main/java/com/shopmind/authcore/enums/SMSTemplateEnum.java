package com.shopmind.authcore.enums;

import lombok.Getter;

@Getter
public enum SMSTemplateEnum {

    /**
     * 登录 / 注册 验证码（共用同一个模板）
     */
    LOGIN_REGISTER("SMS_LOGIN_REGISTER", "登录/注册验证码"),

    /**
     * 绑定手机号验证码
     */
    BIND_PHONE("SMS_BIND_PHONE", "绑定手机号验证码");

    /**
     * 模板 CODE
     */
    private final String codeKey;

    /**
     * 描述
     */
    private final String description;

    SMSTemplateEnum(String codeKey, String description) {
        this.codeKey = codeKey;
        this.description = description;
    }
}