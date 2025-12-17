package com.shopmind.authcore.service;

import com.shopmind.authcore.enums.SMSTemplateEnum;

public interface SMSService {
    void sendShortMessage(String phoneNumber, String smsCode, SMSTemplateEnum smsTemplateEnum);
}
