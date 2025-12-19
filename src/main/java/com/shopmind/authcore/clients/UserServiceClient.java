package com.shopmind.authcore.clients;

import com.shopmind.authcore.dto.request.ResetPasswordRequest;
import com.shopmind.authcore.dto.response.UserResponseDto;
import com.shopmind.framework.context.ResultContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

/**
 * Description: user-service远程服务调用
 * Author: huangcy
 * Date: 2025-12-18
 */
@HttpExchange("/user")
public interface UserServiceClient {

    /**
     * 手机号查询用户
     * @param phoneNumber 手机号
     * @return 用户
     */
    @GetExchange("/by-phone/{phoneNumber}")
    ResultContext<UserResponseDto> getUserByPhone(@PathVariable("phoneNumber") String phoneNumber);

    /**
     * 注册新用户（手机号）
     * @param phoneNumber 手机号
     * @return 用户
     */
    @PostExchange("/register-by-phone")
    ResultContext<UserResponseDto> registerByPhone(@RequestParam String phoneNumber);

    /**
     * 根据 id 查询用户
     * @param userId 用户 id
     * @return 用户
     */
    @GetExchange("/{userId}")
    ResultContext<UserResponseDto> getUserByUserId(@PathVariable("userId") Long userId);

    /**
     * 重置密码（已登录后改密码的场景）
     */
    @PostExchange("/password")
    void updatePassword(@RequestBody ResetPasswordRequest request);

    /**
     * 手机号设置密码（初次登录的场景）
     */
    @PostExchange("/password/{phoneNumber}")
    ResultContext<Void> setPasswordByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber, @RequestBody ResetPasswordRequest request);
}
