package com.picc.chexian.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.picc.chexian.admin.util.security.SpringSecurityUtils;
import com.picc.chexian.core.entity.AdminUser;
import com.picc.chexian.core.service.AdminUserService;

public class BaseController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	protected AdminUserService adminUserService;
	@Autowired
	protected HttpServletRequest request;

	// 取得登录用户
	public AdminUser getUser() {
		String name = SpringSecurityUtils.getCurrentUserName();
		AdminUser user = adminUserService.getUserByName(name);
		return user;
	}
}
