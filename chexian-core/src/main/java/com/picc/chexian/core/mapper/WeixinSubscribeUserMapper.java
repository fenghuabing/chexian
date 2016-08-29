package com.picc.chexian.core.mapper;

import org.apache.ibatis.annotations.Param;

import com.picc.chexian.core.entity.WeixinSubscribeUser;

public interface WeixinSubscribeUserMapper {
	int insert(WeixinSubscribeUser record);

	int deleteByOpenid(String openid);

	WeixinSubscribeUser selectByOpenid(String openid);
	WeixinSubscribeUser selectByUnionid(String unionid);

	int updateInviteCodeByOpenid(@Param(value="openid")String openid, @Param(value="inviteCode")String inviteCode);
	int unsubscribe(String openid);
}
