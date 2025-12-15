package com.shopmind.authcore.service;

import com.shopmind.authcore.dto.Captcha;
import com.shopmind.context.ResultContext;

public interface CaptchaService {
    /**
     * 校验验证码
     **/
    ResultContext<String> checkImageCode(String imageKey, String imageCode);


    /**
     * 获取验证码拼图（生成的抠图和带抠图阴影的大图及抠图坐标）
     **/
    Object getCaptcha(Captcha captcha);
}
