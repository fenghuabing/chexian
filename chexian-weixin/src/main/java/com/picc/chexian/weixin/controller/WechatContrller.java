package com.picc.chexian.weixin.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.picc.chexian.core.entity.WeixinSubscribeUser;
import com.picc.chexian.core.service.WeixinSubscribeUserService;
import com.picc.chexian.weixin.model.Message;
import com.picc.chexian.weixin.service.MessageHanlerService;
import com.picc.chexian.weixin.service.WechatService;
import com.picc.chexian.weixin.util.SHA1;

/**
 * @author sxy 2015年4月20日 下午5:00:03
 * 
 */
//@Controller
public class WechatContrller extends BaseController {
	private static final String AUTH_TOKEN = "xiguamei";
	@Autowired
	MessageHanlerService handler;
	@Autowired
	WeixinSubscribeUserService weixinSubscribeUserService;
	@Autowired
	WechatService wechatService;



	@ResponseBody
	@RequestMapping(value = "wechat")
	public Object index() {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		List<String> list = new ArrayList<String>();
		list.add(AUTH_TOKEN);
		list.add(timestamp);
		list.add(nonce);
		Collections.sort(list);// 参数排序
		String str = "";
		for (String s : list) {
			str = str + s;
		}
		String mySign = DigestUtils.shaHex(str);
		if (!signature.equals(mySign)) {
			// 签名错误
			logger.error("Signature error:signature:{}; mySign:{}", signature,
					mySign);
			return null;
		}

		// 当echostr不为空时，验证接口配置信息，直接返回echostr表示接口有效。
		String echostr = request.getParameter("echostr");
		if (StringUtils.isNotEmpty(echostr)) {
			return echostr;
		}

		String xml = getRequestXmlData(request);
		logger.info("receive data: " + xml);

		try {
			JAXBContext jc = JAXBContext.newInstance(Message.class);
			Unmarshaller unmar = jc.createUnmarshaller();
			Message message = (Message) unmar.unmarshal(new StringReader(xml));
			return handler.handler(message);
		} catch (JAXBException e) {
			logger.error("ERROR:", e);
		}

		return "";
	}
	

	/**
	 * 1、获取用户的推广二维码 2、对weixinJS接口的参数进行SHA1加密
	 * 
	 * @param code
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping({ "share" })
	public Object share(String code, String url, String timestamp, String invite) {
		JSONObject result = new JSONObject();
		String inviteCode = "";
		String signature = "";
		try {
			logger.info("code: " + code+", invite: "+invite);
			JSONObject object = wechatService.getUserInfoFromCode(code);
			String openid = object.getString("openid");
			logger.info("userinfo: " + object);
			
			// 根据openid获取inviteCode
			WeixinSubscribeUser user = weixinSubscribeUserService.findByOpenid(openid);
			inviteCode = (null == user || null == user.getInviteCode()) ? "" : user
					.getInviteCode();
			// 生成signature
			String jsapi = wechatService.getJsapiTicket();
			String params = "jsapi_ticket=%s&noncestr=%s&timestamp=%s&url=%s";
			params = String.format(params, jsapi, AUTH_TOKEN, timestamp, url);
			logger.info("params: " + params);
			signature = new SHA1().Encrypt(params, "SHA-1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("inviteCode", inviteCode);
		result.put("signature", signature);
		logger.info("request url:{}, result:{}", request.getRequestURI(),
				JSONObject.toJSON(result));
		return JSONObject.toJSON(result);
	}


	
	/**
	 * 通过code获取openid和邀请码，供关注用户点击分享菜单时用
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping({ "invite" })
	public Object invite(String code) {
		JSONObject result = new JSONObject();
		try {
			String openid = wechatService.getOpenid(code);
			WeixinSubscribeUser user = weixinSubscribeUserService.findByOpenid(openid);
			String inviteCode = (null == user || null == user.getInviteCode()) ? "" : user
					.getInviteCode();
			result.put("invite", inviteCode);
			result.put("result", 1);
		} catch (Exception e) {
			result.put("result", 0);
			e.printStackTrace();
		}

		return result;
	}
	
	/**
	 * 供被分享用户查看，此时记录查看记录
	 * @param code
	 * @param invite
	 * @return
	 */
	@ResponseBody
	@RequestMapping({ "record" })
	public Object record(String code, String invite) {
		JSONObject result = new JSONObject();
		try {
//			String openid = wechatService.getOpenid(code);
//			JSONObject object = wechatService.getUserInfo(openid);
			JSONObject object = wechatService.getUserInfoFromCode(code);
			
			logger.info("userinfo: " + object);
			result.put("result", 1);
		} catch (Exception e) {
			result.put("result", 0);
			e.printStackTrace();
		}

		return result;
	}

	
	@ResponseBody
	@RequestMapping({ "signature" })
	public Object signature(String timestamp, String url) {
		JSONObject result = new JSONObject();
		try {
			String ticket = wechatService.getJsapiTicket();
			String params = "jsapi_ticket=%s&noncestr=%s&timestamp=%s&url=%s";
			params = String.format(params, ticket, AUTH_TOKEN, timestamp, url);
			logger.info("params: " + params);
			String signature = new SHA1().Encrypt(params, "SHA-1");
			result.put("signature", signature);
			result.put("result", 1);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
		}

		return result;
	}

	private String getRequestXmlData(HttpServletRequest request) {
		InputStream is = null;
		String requestXmlData = null;
		try {
			is = request.getInputStream();
			// 取HTTP请求流长度
			int size = request.getContentLength();
			// 用于缓存每次读取的数据
			byte[] buffer = new byte[size];
			// 用于存放结果的数组
			byte[] xmldataByte = new byte[size];
			int count = 0;
			int rbyte = 0;
			// 循环读取
			while (count < size) {
				// 每次实际读取长度存于rbyte中
				rbyte = is.read(buffer);
				for (int i = 0; i < rbyte; i++) {
					xmldataByte[count + i] = buffer[i];
				}
				count += rbyte;
			}
			requestXmlData = new String(xmldataByte, "UTF-8");
		} catch (IOException e) {
			logger.error("ERROR:", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error("ERROR:", e);
				}
			}
		}
		return requestXmlData;
	}


}
