package com.shopmind.authcore.utils;

import cn.hutool.core.util.StrUtil;
import com.shopmind.authcore.constant.AuthorizationConstant;
import com.shopmind.authcore.exception.AuthServiceException;
import com.shopmind.framework.components.SpringContextHolder;
import com.shopmind.framework.constant.JwtConstants;
import com.shopmind.framework.provider.PublicKeyProvider;
import com.shopmind.framework.util.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;

import java.security.PublicKey;

/**
 * Description: token 相关工具类
 * Author: huangcy
 * Date: 2025-12-18
 */
@Slf4j
public class TokenUtils {
    public static Claims parseLoginToken(String token) {
        try {
            return JwtUtils.parseToken(token, SpringContextHolder.getBean(PublicKeyProvider.class).getPublicKey());
        } catch (ExpiredJwtException e) {
            log.warn("Token 已过期: {}", e.getMessage());
            throw new AuthServiceException("AUTH0009");
        } catch (MalformedJwtException e) {
            log.error("Token 格式非法: {}", e.getMessage());
            throw new AuthServiceException("AUTH0010");
        } catch (Exception e) {
            log.error("Token 解析失败: {}", e.getMessage());
            throw new AuthServiceException("AUTH0011");
        }
    }

    /**
     * 去掉 "Bearer "
     * @param authHeader authorization 请求头
     * @return 纯 token
     */
    public static String extractTokenFromHeader(String authHeader) {
        if (authHeader == null || !authHeader.startsWith(JwtConstants.TOKEN_PREFIX)) {
            return null;
        }
        return authHeader.substring(JwtConstants.TOKEN_PREFIX.length()).trim();
    }

    /**
     * 校验 短信验证码 token
     */
    public static boolean validateSMSToken(String phoneNumber, String smsCode, String smsToken) {
        try {
            Claims claims = JwtUtils.parseToken(smsToken, SpringContextHolder.getBean(PublicKeyProvider.class).getPublicKey());
            String phoneNumberDecoded = claims.get(AuthorizationConstant.PHONE_NUMBER, String.class);
            String smsCodeDecoded = claims.get(AuthorizationConstant.SMS_CODE, String.class);
            return StrUtil.equals(phoneNumber, phoneNumberDecoded) && StrUtil.equals(smsCodeDecoded, smsCode);
        } catch (ExpiredJwtException e) {
            throw new AuthServiceException("AUTH0018");
        } catch (MalformedJwtException e) {
            throw new AuthServiceException("AUTH0010");
        } catch (Exception e) {
            throw new AuthServiceException("AUTH0011");
        }
    }
}
