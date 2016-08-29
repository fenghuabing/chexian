package com.picc.chexian.weixin.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.picc.chexian.core.service.BaseService;
import com.picc.chexian.core.util.HttpService;
import com.picc.chexian.weixin.util.HttpsPostRequest;

@Service
public class WechatService extends BaseService {
	@Autowired
	HttpService 	httpService;
    
    // 西瓜妹
    public static final String APPID_XIGUAMEI       = "wxbcbd557ee763bf7c";
    public static final String APPSECRET_XIGUAMEI   = "46d5922acf5c18b6c86f5eced5ca4d11";

    private static Logger      logger       = LoggerFactory.getLogger(WechatService.class.getName());
    private static String      ACCESS_TOKEN_GLOBAL  = null;
    private static String      REFRESH_TOKEN = null;
    private static String      ACCESS_TOKEN  = null;
    private static Date        TOKEN_TIME_GLOBAL    = null;
    private static Date        TOKEN_TIME    = null;
    
    public String getInviteCode(String json){
    	String url = "http://www.xiguamei.com/api/user/sync";
    	String result = httpService.postJson(url, json);
    	if (StringUtils.isNotBlank(result)) {
    		JSONObject jsonObject = JSONObject.parseObject(result);
    		if (jsonObject.containsKey("invite_code")) {
    			return jsonObject.getString("invite_code");
    		}
    	}
    	return null;
    }
    
    public String getXGMOpenid(String code) {
    	String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    	url = String.format(url, APPID_XIGUAMEI, APPSECRET_XIGUAMEI, code);
        String result = httpService.get(url);
//    	String result = HttpRequest.sendHttpRequest(url, "GET", "UTF-8");
    	if (StringUtils.isNotBlank(result)) {
    		JSONObject json = JSONObject.parseObject(result);
    		if (json.containsKey("openid")) {
    			return json.getString("openid");
    		}
    	}
    	return null;
    }

    public String getOpenid(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
        url = String.format(url, APPID_XIGUAMEI, APPSECRET_XIGUAMEI, code);
        String result = httpService.get(url);
//        String result = HttpRequest.sendHttpRequest(url, "GET", "UTF-8");
        if (StringUtils.isNotBlank(result)) {
            JSONObject json = JSONObject.parseObject(result);
            if (json.containsKey("openid")) {
                return json.getString("openid");
            }
//            if (json.containsKey("refresh_token")){
//            	REFRESH_TOKEN = json.getString("refresh_token");
//            	refreshToken();
//            }
//            if (StringUtils.isBlank(ACCESS_TOKEN)){
////                if (json.containsKey("access_token")){
////                	tokenTime = new Date();
////                	ACCESS_TOKEN = json.getString("access_token");
////                }
//            }
//            if (json.containsKey("openid")) {
//                return json.getString("openid");
//            }
        }
        return null;
    }

    public JSONObject getUserInfoFromCode(String code){
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
        url = String.format(url, APPID_XIGUAMEI, APPSECRET_XIGUAMEI, code);
        String result = httpService.get(url);
        if (StringUtils.isNotBlank(result)) {
            JSONObject json = JSONObject.parseObject(result);
			if (json.containsKey("access_token")) {
				String token = json.getString("access_token");
				String openid = json.getString("openid");
		    	url = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";
		        url = String.format(url, token, openid);
		        result = httpService.get(url);
//		        String result = HttpRequest.sendHttpRequest(url, "GET", "UTF-8");
		        return JSONObject.parseObject(result);
			}
        }
        return null;
    }

    public JSONObject getUserInfo(String openid){
    	String url = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";
        String token = getToken();
        url = String.format(url, token, openid);
        String result = httpService.get(url);
//        String result = HttpRequest.sendHttpRequest(url, "GET", "UTF-8");
        JSONObject json = JSONObject.parseObject(result);
        return json;
    }
    
    public JSONObject getInfo(String openid){
    	String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";
    	String token = getTokenGlobal();
    	url = String.format(url, token, openid);
    	String result = httpService.get(url);
//    	String result = HttpRequest.sendHttpRequest(url, "GET", "UTF-8");
    	JSONObject json = JSONObject.parseObject(result);
    	return json;
    }
    
    // 每60分钟修改一次ACCESSTOKEN_GLOBAL
    public String getTokenGlobal() {
    	if (null != ACCESS_TOKEN_GLOBAL &&  1 > compareTime(TOKEN_TIME_GLOBAL, new Date())) {
    		logger.info("ACCESSTOKEN_GLOBAL: " + ACCESS_TOKEN_GLOBAL);
    		return ACCESS_TOKEN_GLOBAL;
    	} else {
    		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    		url = String.format(url, APPID_XIGUAMEI, APPSECRET_XIGUAMEI);
    		String result = httpService.get(url);
//    		String result = HttpRequest.sendHttpRequest(url, "GET", "UTF-8");
    		if (!StringUtils.isBlank(result)) {
    			JSONObject jsonObject = JSONObject.parseObject(result);
    			if (jsonObject.containsKey("access_token")) {
    				TOKEN_TIME_GLOBAL = new Date();
    				ACCESS_TOKEN_GLOBAL = jsonObject.getString("access_token");
    			}
    		}
    	}
    	logger.info("ACCESSTOKEN_GLOBAL: " + ACCESS_TOKEN_GLOBAL);
    	return ACCESS_TOKEN_GLOBAL;
    }

	// 每110分钟修改一次ACCESSTOKEN
    public String getToken() {
        if (null != ACCESS_TOKEN && !isTimeOut(TOKEN_TIME)) {
            logger.info("ACCESS_TOKEN: " + ACCESS_TOKEN);
            return ACCESS_TOKEN;
        } else {
        	refreshToken();
        }
        logger.info("ACCESSTOKEN: " + ACCESS_TOKEN);
        return ACCESS_TOKEN;
    }
    
    private void refreshToken(){
    	String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=%s&grant_type=refresh_token&refresh_token=%s";
        url = String.format(url, APPID_XIGUAMEI, REFRESH_TOKEN);
        String result = httpService.get(url);
//        String result = HttpRequest.sendHttpRequest(url, "GET", "UTF-8");
        if (!StringUtils.isBlank(result)) {
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (jsonObject.containsKey("access_token")) {
                TOKEN_TIME = new Date();
                ACCESS_TOKEN = jsonObject.getString("access_token");
            }
        }
    }
    
    @Async
    private void asyncRefreshToken(){
    	refreshToken();
    }

    /**
     * 微信超时时间是2小时，本地超时时间是110分钟
     */
    private final long timeout = 110*60*1000;
    
    private boolean isTimeOut(Date begin) {
    	return System.currentTimeMillis() - begin.getTime()>timeout;
    }


    public String getTicket(Long userid) throws Exception {
        String token = getTokenGlobal();
        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=".concat(token);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action_name", "QR_LIMIT_SCENE");
        JSONObject scene = new JSONObject();
        JSONObject sceneid = new JSONObject();
        sceneid.put("scene_id", userid);
        scene.put("scene", sceneid);
        jsonObject.put("action_info", scene);
        String params = jsonObject.toString();
        String result = HttpsPostRequest.postReq(url, params);
        if (!StringUtils.isBlank(result)) {
            JSONObject json = JSONObject.parseObject(result);
            if (json.containsKey("ticket")) {
                return json.getString("ticket");
            }
        }
        return null;
    }

    public String getJsapiTicket() {
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token=" + getTokenGlobal();
        String result = httpService.get(url);
//        String result = HttpRequest.sendHttpRequest(url, "GET", "UTF-8");
        if (!StringUtils.isBlank(result)) {
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (jsonObject.containsKey("ticket")) {
                return jsonObject.getString("ticket");
            }
        }
        return null;
    }

    private long compareTime(Date begin, Date end) {
        long beginTime = begin.getTime();
        long endTime = end.getTime();
        long between = (endTime - beginTime) / 1000;
        long day = between / (24 * 3600);
        long hour = between % (24 * 3600) / 3600;
//        long minute = between % 3600 / 60;
//        long second = between % 60 / 60;
        return day*24+hour;
    }
}
