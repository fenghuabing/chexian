package com.picc.chexian.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.picc.chexian.core.entity.WeixinSubscribeUser;
import com.picc.chexian.core.mapper.WeixinSubscribeUserMapper;

@Service
public class WeixinSubscribeUserService extends BaseService {
	@Autowired
	WeixinSubscribeUserMapper mapper;
    
    public int create(WeixinSubscribeUser record){
    	int result = mapper.insert(record);
    	return result;
    }
	
	public WeixinSubscribeUser findByOpenid(String openid){
		return mapper.selectByOpenid(openid);
	}

	public WeixinSubscribeUser findByUnionid(String unionid){
		return mapper.selectByUnionid(unionid);
	}

	public int updateInviteCodeByOpenid(String openid, String inviteCode) {
		return mapper.updateInviteCodeByOpenid(openid, inviteCode);
	}
	
	public int unsubscribe(String openid){
		return mapper.unsubscribe(openid);
	}
}
