package com.picc.chexian.core.util;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.picc.chexian.core.enums.SMSType;
import com.picc.chexian.core.service.BaseService;
import com.picc.chexian.core.service.RedisClient;

@Service
public class ConfirmService extends BaseService {
	
    @Autowired
    SMSService smsService;
	@Autowired
	RedisClient redis;

    public boolean sendCaptcha(String mobile, SMSType type) {
    	String key = getKey(mobile, type);
    	Long ttl = redis.ttl(key);
    	if (ttl == -2){
    		//当 key 不存在时，返回 -2 。
    		String captcha = RandomStringUtils.randomNumeric(4);
            try {
    			smsService.sendMessage(type, mobile, captcha);
    		} catch (IOException e) {
    			return false;
    		}
    		redis.setString(key, captcha);
    		redis.expire(key, 10*60);
    		logger.info("Captcha " + captcha + " is sent to " + mobile + " at " + (new Date().toString()));
            return true;
    	}
    	else if (ttl == -1){
    		return false;
    	}
    	else{
    		String captcha = redis.getString(key);
    		if (ttl > 9*60){
        		//在一分钟之内，不重复发送
        		logger.debug("Captcha " + captcha + " has sent to " + mobile + " in 1 minute");
    			return false;
    		}else{
    			//超过一分钟的，再次发送同样的验证码，并重置失效时间
                try {
        			smsService.sendMessage(type, mobile, captcha);
        		} catch (IOException e) {
        			return false;
        		}
    			redis.expire(key, 10*60);
        		logger.info("Captcha " + captcha + " is sent to " + mobile + " at " + (new Date().toString()));
                return true;
    		}
    	}
    }

    public String checkMobile(String mobile, SMSType type) {
    	String key = getKey(mobile, type);
    	return redis.getString(key);
    }    
    
    private String getKey(String mobile, SMSType type) {
    	return type.getKey()+":"+mobile;
    }

}
