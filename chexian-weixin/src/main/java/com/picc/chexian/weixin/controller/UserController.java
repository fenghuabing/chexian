package com.picc.chexian.weixin.controller;

import java.security.Principal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.picc.chexian.core.entity.AdminUser;
import com.picc.chexian.core.service.AdminUserService;
import com.picc.chexian.core.util.MD5Util;

@Controller
public class UserController extends BaseController{

//	public final static String USER_SESSION_KEY = "user_session_key";
//
//	@Autowired
//	protected AdminUserService adminUserService;
//
//	@RequestMapping(value = "/index")
//	public String loginSuccess(Principal principal) {
//		String name = principal.getName();
//		AdminUser user = adminUserService.getUserByName(name);
//		request.getSession().setAttribute(USER_SESSION_KEY, user);
//		return "redirect:/garage/list";
//	}

	@Autowired
	private AdminUserService adminUserService;

	@RequestMapping(value = "/login")
	public String login(ModelMap map, Integer error) {
		if (error != null) {
			if (error.intValue() == 1)
				map.addAttribute("errorMessage", "用户名或密码错误!");
			else if (error.intValue() == 3)
				map.addAttribute("errorMessage", "用户已从别处登录!");
		}
		return "/login";
	}

	@RequestMapping(value = "/denied")
	public String loginerror(ModelMap map) {
		return "/common/403";
	}

	@RequestMapping(value = "/logout")
	public String logout(Principal principal) {
		String name = principal.getName();
		logger.debug(name+" logout called...");
		return "/login";
	}
	
	@RequestMapping(value = "/passwd")
	public String passwd(Principal principal, String password, ModelMap map) {
		if (StringUtils.isNotEmpty(password)){
			String name = principal.getName();
			AdminUser user = adminUserService.getUserByName(name);
			user.setPassword(MD5Util.md5(password));
			int effectRows = adminUserService.updateUser(user);
			//如果修改成功
			if(effectRows > 0) {
				map.put("success", true);
				map.put("message", "修改密码成功！");
			} else { //如果修改失败
				map.put("success", false);
				map.put("message", "修改密码失败！请与开发人员联系。");
			}
		}
		return "/passwd";
	}

}
