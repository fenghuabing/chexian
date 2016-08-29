package com.picc.chexian.weixin.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.picc.chexian.core.entity.WeixinSubscribeUser;
import com.picc.chexian.core.service.WeixinSubscribeUserService;
import com.picc.chexian.weixin.model.EventType;
import com.picc.chexian.weixin.model.Message;

@Service
public class MessageHanlerService {
	protected Logger logger = LoggerFactory.getLogger(MessageHanlerService.class);

	@Autowired
	WeixinSubscribeUserService weixinSubscribeUserService;
	@Autowired
	WechatService wechatService;
	
	
	public String handler(Message message){
		if (message == null){
			return null;
		}
		String msgType = message.getMsgType();
		String event = message.getEvent();
		
		if ("event".equals(msgType)){
			if (EventType.SUB.equals(event)){
				return subscribe(message);
			}
			else if (EventType.UNSUB.equals(event)){
				unsubscribe(message);
			}
		}
		return "";			
	}
	
	
	// 用户关注后的消息处理
	private String subscribe(Message message) {
		String nickname = "";
		if (null != message) {
			String openid = message.getFromUserName();
			WeixinSubscribeUser existUser = weixinSubscribeUserService
					.findByOpenid(openid);
			JSONObject result = wechatService.getInfo(openid);
			nickname = result.getString("nickname");
			if (null == existUser || null==existUser.getOpenid()) {
				WeixinSubscribeUser user = toUser(result);
				weixinSubscribeUserService.create(user);
				logger.info("userinfo: "+result);
			}
			JSONObject param = new JSONObject();
			param.put("unionid", result.getString("unionid"));
			param.put("nickname", result.getString("nickname"));
			param.put("headimgurl", result.getString("headimgurl"));
			param.put("country", result.getString("country"));
			param.put("province", result.getString("province"));
			param.put("city", result.getString("city"));
			param.put("language", result.getString("language"));
			param.put("sex", result.getInteger("sex"));
			String inviteCode = wechatService.getInviteCode(param.toString());
			logger.info("inviteCode: "+inviteCode);
			weixinSubscribeUserService.updateInviteCodeByOpenid(openid, inviteCode);
		}
		return getSubscribeMsg(message, nickname);
	}
	
	// 用户取消关注后的消息处理
	private void unsubscribe(Message message) {
		if (null != message) {
			weixinSubscribeUserService.unsubscribe(message.getFromUserName());
		}
	}

	private WeixinSubscribeUser toUser(JSONObject json) {
		if (null == json || json.containsKey("errcode")) {
			return null;
		}
		WeixinSubscribeUser user = new WeixinSubscribeUser();
		user.setOpenid(json.getString("openid"));
		String nickname = json.getString("nickname");
		user.setNickname(null == nickname ? "" : nickname);
		Short sex = json.getShort("sex");
		user.setSex(null == sex ? 0 : sex);
		String language = json.getString("language");
		user.setLanguage(null == language ? "" : language);
		String city = json.getString("city");
		user.setCity(null == city ? "" : city);
		String province = json.getString("province");
		user.setProvince(null == province ? "" : province);
		String country = json.getString("country");
		user.setCountry(null == country ? "" : country);
		String headimgurl = json.getString("headimgurl");
		user.setHeadimgurl(null == headimgurl ? "" : headimgurl);
		String unionid = json.getString("unionid");
		user.setUnionid(null == unionid ? "" : unionid);
		user.setCreateTime(new Date());
		return user;
	}

	private String getSubscribeMsg(Message message, String nickname) {
		nickname = ("".equals(nickname)) ? "小伙伴" : nickname;
		String result = "<xml><ToUserName><![CDATA[%s]]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime><![CDATA[%s]]></CreateTime><MsgType>news</MsgType><ArticleCount>1</ArticleCount><Articles><item><Title><![CDATA[%s]]></Title><Description><![CDATA[%s]]></Description><PicUrl><![CDATA[%s]]></PicUrl><Url><![CDATA[%s]]></Url></item></Articles></xml>";
		String title = "开心游戏 轻松赚钱！";
		String description = "Hi，NAME！欢迎关注我们西瓜妹，在西瓜妹每次做任务都可以获取一份现金奖励。开心做任务，轻松赚零花。从“西瓜妹”开始！";
		description = description.replace("NAME", nickname);
		String picurl = "http://wechat.xiguamei.com/wechat/images/xiguamei.png";
		String url = "http://mp.weixin.qq.com/s?__biz=MzAwNTY5MjM3Nw==&mid=214307377&idx=1&sn=b8a235f7eb7d519ce8621a7d3a1e471c#rd";
		result = String.format(result, message.getFromUserName(), message.getToUserName(),  new Date().getTime(), title, description, picurl, url);
		logger.debug("subscirbeMsg: "+result);
		return result;
	}

}
