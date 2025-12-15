package com.shopmind.authcore.controller;

import com.shopmind.authcore.dto.Captcha;
import com.shopmind.authcore.dto.VerifyCaptchaDto;
import com.shopmind.framework.context.ResultContext;
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

    @PostMapping
    public ResultContext<Object> getCaptcha() {
        return ResultContext.success(captchaService.getCaptcha(new Captcha(1)));
    }

    @PostMapping("/verify")
    public ResultContext<String> verifyCaptcha(@RequestBody VerifyCaptchaDto  verifyCaptchaDto) {
        return captchaService.checkImageCode(verifyCaptchaDto.getImageKey(), verifyCaptchaDto.getBlockX());
    }
}