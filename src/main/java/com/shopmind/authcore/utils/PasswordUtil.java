package com.shopmind.authcore.utils;

import cn.hutool.core.util.StrUtil;
import com.shopmind.authcore.exception.AuthServiceException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Description: password 进行加密和检验
 * Author: huangcy
 * Date: 2025-12-19
 */
public class PasswordUtil {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * 对明文密码进行哈希（用于注册/修改密码）
     */
    public static String encode(String rawPassword) {
        if (StrUtil.isBlank(rawPassword)) {
            throw new AuthServiceException("AUTH0012");
        }
        return encoder.encode(rawPassword);
    }

    /**
     * 验证明文密码是否与哈希匹配（用于登录）
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        if (rawPassword == null || encodedPassword == null) {
            return false;
        }
        return encoder.matches(rawPassword, encodedPassword);
    }
}
