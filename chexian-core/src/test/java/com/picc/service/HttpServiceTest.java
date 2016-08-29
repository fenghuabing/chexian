package com.picc.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.picc.AbstractSpringTest;
import com.picc.chexian.core.util.HttpService;

public class HttpServiceTest extends AbstractSpringTest {

	@Autowired
	HttpService httpService;
	
 	@Test
	public void test(){
		String a = "{\"city\":\"\",\"country\":\"根廷\",\"groupid\":0,\"headimgurl\":\"http://wx.qlogo.cn/mmopen/EhlBsINlALyQVibyCY7AJ8foQf2m1EHhvZxD52BfpLNlNPX7JppAVH5ZiciaxRxmNqQvWqo730ib3A79p64Wlx93fsfeB4MKfYFg/0\",\"language\":\"zh_CN\",\"nickname\":\"Ten zuiden van bloem.\",\"openid\":\"o2Tkns7ZrTCFfr6HRHKAH27ZAI8c\",\"province\":\"圣菲\",\"remark\":\"\",\"sex\":1,\"subscribe\":1,\"subscribe_time\":1461949886,\"tagid_list\":[],\"unionid\":\"oPSnRvn3kMK9AVw_KU4aunqfBv9A\"}";
		JSONObject result = JSONObject.parseObject(a);
		JSONObject param = new JSONObject();
		param.put("unionid", result.getString("unionid"));
		param.put("nickname", result.getString("nickname"));
		param.put("headimgurl", result.getString("headimgurl"));
		param.put("country", result.getString("country"));
		param.put("province", result.getString("province"));
		param.put("city", result.getString("city"));
		param.put("language", result.getString("language"));
		param.put("sex", result.getInteger("sex"));
    	String url = "http://www.xiguamei.com/api/user/sync";
		logger.info(param.toString());
    	String ret = httpService.postJson(url, param.toString());
    	logger.info("result:{}", ret);
}
	
 	@Test
	public void testJSON(){
    	String url = "http://www.xiguamei.com/api/user/sync";
    	String json = "{\"city\":\"\",\"country\":\"\",\"groupid\":0,\"headimgurl\":\"http://wx.qlogo.cn/mmopen/zSiaCXvUvwfxwfEErU1lnJR87Iz0EibGJnsYTjSN8WaR6SHzJaIAqOcJQ1KdtiaXf8Ij5TVyYiakicYHzwkCeeujWLVlpQdEQqO4Z/0\",\"language\":\"en\",\"nickname\":\"langcheng.lai\",\"openid\":\"o2Tkns9XapJgNtt0EU9MJiC4ytlU\",\"province\":\"\",\"remark\":\"\",\"sex\":0,\"subscribe\":1,\"subscribe_time\":1462258207,\"tagid_list\":[],\"unionid\":\"oPSnRvglHseasVHugFseE3xYDuRY\"}";
    	String result = httpService.postJson(url, json);
    	logger.info("result:{}", result);
    }

}
