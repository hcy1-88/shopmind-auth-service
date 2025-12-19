package com.shopmind.authcore.service.impl;

import cn.hutool.core.util.StrUtil;
import com.shopmind.authcore.clients.UserServiceClient;
import com.shopmind.authcore.constant.AuthorizationConstant;
import com.shopmind.authcore.dto.request.ResetPasswordRequest;
import com.shopmind.authcore.dto.response.LoginResponseDto;
import com.shopmind.authcore.dto.response.UserResponseDto;
import com.shopmind.authcore.exception.AuthServiceException;
import com.shopmind.authcore.service.AuthorizationService;
import com.shopmind.authcore.service.SMSService;
import com.shopmind.authcore.service.TokenService;
import com.shopmind.authcore.utils.PasswordUtil;
import com.shopmind.authcore.utils.TokenUtils;
import com.shopmind.framework.constant.JwtConstants;
import com.shopmind.framework.context.ResultContext;
import com.shopmind.framework.provider.PublicKeyProvider;
import com.shopmind.framework.util.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

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
        if (!TokenUtils.validateSMSToken(phoneNumber, code, smsToken)) {
            throw new AuthServiceException("AUTH0008");
        }
        LoginResponseDto res =  new LoginResponseDto();
        // 2，向 user-service 获取用户
        UserResponseDto userByPhone = userServiceClient.getUserByPhone(phoneNumber);
        if (userByPhone == null){
            // 3，不存在则创建用户
            UserResponseDto userCreated = userServiceClient.registerByPhone(phoneNumber);
            res.setUser(userCreated);
        } else {
            res.setUser(userByPhone);
        }
        res.setToken(tokenService.generateAccessToken(res.getUser()));
        // 4，返回用户 和 access token
        return res;
    }

    @Override
    public UserResponseDto getMeInfo(String token) {
        Claims claims = TokenUtils.parseLoginToken(token);
        Long userId = claims.get(JwtConstants.JWT_USER_ID, Long.class);
        return userServiceClient.getUserByUserId(userId);
    }

    @Override
    public ResultContext<LoginResponseDto> loginByPwd(String phoneNumber, String password) {
        // 1，根据手机号查用户
        UserResponseDto userByPhone = userServiceClient.getUserByPhone(phoneNumber);
        if (userByPhone == null){
            throw new AuthServiceException("AUTH0013");
        }
        // 2, 密码为空，说明第一次以密码登录，需要告知前端，以跳转密码设置
        if (StrUtil.isEmpty(userByPhone.getPasswordHash())) {
            return ResultContext.fail(AuthorizationConstant.NO_PASSWORD_SET);
        }
        // 3，密码校验
        if (PasswordUtil.matches(password, userByPhone.getPasswordHash())) {
            LoginResponseDto res =  new LoginResponseDto();
            res.setUser(userByPhone);
            res.setToken(tokenService.generateAccessToken(userByPhone));
            return ResultContext.success(res);
        } else {
            throw new AuthServiceException("AUTH0014");
        }
    }

    @Override
    public void setPasswordFirstTime(String phoneNumber, String smsCode, String smsToken, String password, String confirmPassword) {
        if (!TokenUtils.validateSMSToken(phoneNumber, smsToken, smsToken)) {
            throw new AuthServiceException("AUTH0008");
        }
        userServiceClient.setPasswordByPhoneNumber(phoneNumber, new ResetPasswordRequest(PasswordUtil.encode(password)));
    }

    @Override
    public void resetPassword(String password, String confirmPassword) {
        try {
            // 1. 校验非空
            if ( StrUtil.isEmpty(password) || StrUtil.isEmpty(confirmPassword)) {
                throw new AuthServiceException("AUTH0015");
            }

            // 2. 校验两次密码是否一致（关键！）
            if (!StrUtil.equals(password, confirmPassword)) {
                throw new AuthServiceException("AUTH0016");
            }

            // 3. 加密并保存
            String hashedPassword = PasswordUtil.encode(password);
            userServiceClient.updatePassword(new ResetPasswordRequest(hashedPassword));
        } catch (HttpClientErrorException.Unauthorized e) {
            throw new AuthServiceException("AUTH0007");
        } catch (HttpClientErrorException ex) {
            log.error("UserService HTTP 客户端错误: {}", ex.getStatusCode(), ex);
            throw new AuthServiceException("AUTH0017");
        }

    }
}
