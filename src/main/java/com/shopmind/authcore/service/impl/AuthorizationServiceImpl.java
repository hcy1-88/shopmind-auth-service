package com.shopmind.authcore.service.impl;

import cn.hutool.core.util.StrUtil;
import com.shopmind.authcore.clients.UserServiceClient;
import com.shopmind.authcore.constant.AuthorizationConstant;
import com.shopmind.authcore.dto.response.LoginResponseDto;
import com.shopmind.authcore.dto.response.UserResponseDto;
import com.shopmind.authcore.exception.AuthServiceException;
import com.shopmind.authcore.service.AuthorizationService;
import com.shopmind.authcore.service.SMSService;
import com.shopmind.authcore.service.TokenService;
import com.shopmind.authcore.utils.TokenUtils;
import com.shopmind.framework.constant.JwtConstants;
import com.shopmind.framework.context.ResultContext;
import com.shopmind.framework.provider.PublicKeyProvider;
import com.shopmind.framework.util.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Description: 认证服务
 * Author: huangcy
 * Date: 2025-12-17
 */
@Slf4j
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Resource
    private SMSService smsService;

    @Resource
    private TokenService tokenService;

    @Resource
    private PublicKeyProvider publicKeyProvider;

    @Resource
    private UserServiceClient userServiceClient;

    @Override
    public String sendShorMsgForLoginOrRegister(String phoneNumber) {
        // todo 开发阶段先固定验证码，不发短信
//        String code = SMSUtil.generateSMSCode();
        String code ="123456";
//        smsService.sendShortMessage(phoneNumber, code, SMSTemplateEnum.LOGIN_REGISTER);
        return tokenService.generateVerificationCodeToken(phoneNumber, code);
    }

    @Override
    public LoginResponseDto smsLoginAndRegister(String phoneNumber, String code, String smsToken) {
        // 1，校验验证码令牌
        Claims claims = JwtUtils.parseToken(smsToken, publicKeyProvider.getPublicKey());
        String phoneNumberDecoded = claims.get(AuthorizationConstant.PHONE_NUMBER, String.class);
        String smsCodeDecoded = claims.get(AuthorizationConstant.SMS_CODE, String.class);
        if (!StrUtil.equals(phoneNumber, phoneNumberDecoded) || !StrUtil.equals(smsCodeDecoded, code)) {
            log.warn("手机号 {} 与验证码 {} 不匹配", phoneNumber, code);
            throw new AuthServiceException("AUTH0008");
        }
        LoginResponseDto res =  new LoginResponseDto();
        // 2，向 user-service 获取用户
        ResultContext<UserResponseDto> userByPhone = userServiceClient.getUserByPhone(phoneNumber);
        if (userByPhone.getData() == null){
            // 3，不存在则创建用户
            ResultContext<UserResponseDto> userCreated = userServiceClient.registerByPhone(phoneNumber);
            res.setUser(userCreated.getData());
        } else {
            res.setUser(userByPhone.getData());
        }
        res.setToken(tokenService.generateAccessToken(res.getUser()));
        // 4，返回用户 和 access token
        return res;
    }

    @Override
    public UserResponseDto getMeInfo(String token) {
        Claims claims = TokenUtils.parseLoginToken(token);
        Long userId = claims.get(JwtConstants.JWT_USER_ID, Long.class);
        return userServiceClient.getUserByUserId(userId + "").getData();
    }
}
