package com.shopmind.authcore.dto;

import lombok.Data;

import java.util.Date;

/**
 * 用户实体类
 */
@Data
public class Users {
    /**
     * 用户ID（分布式ID）
     */
    private Long id;

    /**
     * 手机号（可为空，微信登录用户可能没有手机号）
     */
    private String phone;

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
}