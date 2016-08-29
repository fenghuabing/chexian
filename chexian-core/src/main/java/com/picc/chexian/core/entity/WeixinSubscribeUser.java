package com.picc.chexian.core.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class WeixinSubscribeUser implements Serializable{
	private static final long serialVersionUID = 1L;

	private String openid;

	private String unionid;

	private String nickname;

    private Short sex;

    private String language;

    private String city;

    private String province;

    private String country;

    private String headimgurl;

    private Date createTime;

    private Date updateTime;

    private String inviteCode;

    private Boolean enable;
}