package com.shopmind.authcore.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 用户实体类
 */
@Data
public class UserResponseDto {
    /**
     * 用户ID（分布式ID）
     */
    @JsonFormat(shape =  JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 手机号（可为空，微信登录用户可能没有手机号）
     */
    private String phoneNumber;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 性别：male/female/other
     */
    private String gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 软删除时间
     */
    private Date deletedAt;

    /**
     * 单向 Hash 加密过的密码
     */
    private String passwordHash;
}