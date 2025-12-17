package com.shopmind.authcore.exception;

import com.shopmind.framework.exception.ShopmindException;
import com.shopmind.framework.util.MessageSourceHelper;

import java.util.Map;

public class AuthServiceException extends ShopmindException {


    /**
     * 资源文件基础名称
     * 对应 resources 目录下的 example_message_zh_CN.properties
     */
    private static final String RESOURCE_BASE_NAME = "authservice_message";

    /**
     * 构造方法1：只传入错误码
     * 从国际化文件中读取错误消息
     *
     * @param code 错误码
     */
    public AuthServiceException(String code) {
        super(code, MessageSourceHelper.getMessage(RESOURCE_BASE_NAME, code));
    }

    /**
     * 构造方法2：传入错误码和参数
     * 从国际化文件中读取错误消息模板，并填充参数
     *
     * @param code 错误码
     * @param args 参数
     */
    public AuthServiceException(String code, Object... args) {
        super(code, MessageSourceHelper.getMessage(RESOURCE_BASE_NAME, code, args));
    }

    /**
     * 构造方法3：直接指定错误码和消息（不使用国际化）
     *
     * @param code    错误码
     * @param message 错误消息
     */
    public AuthServiceException(String code, String message) {
        super(code, message);
    }

    /**
     * 构造方法4：带上下文信息
     *
     * @param code    错误码
     * @param context 上下文信息
     * @param args    参数
     */
    public AuthServiceException(String code, Map<String, Object> context, Object... args) {
        super(code, MessageSourceHelper.getMessage(RESOURCE_BASE_NAME, code, args), context);
    }

    /**
     * 构造方法5：带原始异常
     *
     * @param code  错误码
     * @param cause 原始异常
     * @param args  参数
     */
    public AuthServiceException(String code, Throwable cause, Object... args) {
        super(code, MessageSourceHelper.getMessage(RESOURCE_BASE_NAME, code, args), cause);
    }
}