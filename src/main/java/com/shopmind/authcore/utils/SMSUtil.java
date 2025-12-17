package com.shopmind.authcore.utils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Description: 短信工具类
 * Author: huangcy
 * Date: 2025-12-17
 */
public class SMSUtil {
    // 6 位数字
    public static String generateSMSCode() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(10)) +
                ThreadLocalRandom.current().nextInt(10) +
                ThreadLocalRandom.current().nextInt(10) +
                ThreadLocalRandom.current().nextInt(10) +
                ThreadLocalRandom.current().nextInt(10) +
                ThreadLocalRandom.current().nextInt(10);
    }
}
