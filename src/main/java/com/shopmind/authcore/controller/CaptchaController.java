package com.shopmind.authcore.controller;

import com.shopmind.dto.Captcha;
import com.shopmind.context.ResultContext;
import com.shopmind.authcore.service.CaptchaService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @Resource
    private CaptchaService captchaService;

    @PostMapping("get-captcha")
    public ResultContext<Object> getCaptcha(@RequestBody Captcha captcha) {
        return ResultContext.success(captchaService.getCaptcha(captcha));
    }
}