package com.picc.chexian.admin.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.picc.chexian.admin.dto.UserDTO;
import com.picc.chexian.admin.util.JsonTransfer;
import com.picc.chexian.core.entity.AdminUser;
import com.picc.chexian.core.entity.Area;
import com.picc.chexian.core.entity.Role;
import com.picc.chexian.core.service.AdminUserService;
import com.picc.chexian.core.service.AreaService;
import com.picc.chexian.core.service.RoleService;
import com.picc.chexian.core.util.MD5Util;


@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController{
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private RoleService roleService;

	@RequestMapping("/list")
	public String list(ModelMap map){
		map.put("user", getUser());
		return "user/list";
	}

	@ResponseBody
	@RequestMapping("/load")
	public Object load(){
		try {
			String sEcho = request.getParameter("draw");// 搜索内容
			String searchContent = request.getParameter("search[value]");
			if (searchContent != null){
				searchContent = searchContent.trim();
			}
			AdminUser user = getUser();
			List<AdminUser> users = null;
			if (user.getLevel()==1){
				users = adminUserService.selectLevel2(searchContent);
			}
			else if (user.getLevel()==2){
				users = adminUserService.selectLevel3(user.getCityId(), searchContent);
			}
			String result = JsonTransfer.getJsonFromList(sEcho, users);
			result = "{\"draw\":" + sEcho + ",\"recordsTotal\":" + 1
					+ ",\"recordsFiltered\":" + users.size() + ",\"data\":" + result + "}";
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping("/add")
	public String add(ModelMap map){
		AdminUser admin = getUser();
		map.put("admin", admin);
		if (admin.getLevel() == 1){
			List<Area> provinces = areaService.selectByPid("0");
			map.put("provinces", provinces);
		}
		return "user/create";
	}
	
	@ResponseBody
	@RequestMapping("/city")
	public Object city(String prov){
		List<Area> cities = areaService.selectByPid(prov);
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		for (Area a: cities){
			JSONObject item = new JSONObject();
			item.put("id", a.getVc2areaGuid());
			item.put("name", a.getVc2areaName());
			array.add(item);
		}
		result.put("cities", array);
		return result;
	}

	/**
	 * 添加一个用户，完成后返回用户列表页
	 * @param user
	 * @param userType
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/create")
	public Object create(AdminUser user) {
		JSONObject result = new JSONObject();
		try {
			AdminUser admin = getUser();

			//填充user空的属性
			Date now = new Date();
			user.setCreateTime(now);
			user.setModifyTime(now);
			String password = user.getPassword().trim();
			password = MD5Util.md5(password);
			user.setPassword(password);
			user.setIsValid(1);
			user.setLevel((byte)(admin.getLevel()+1));
			if (admin.getLevel()==2){
				user.setCityId(admin.getCityId());
			}
			//保存user并返回id
			int id = adminUserService.addUser(user);
			//如果添加成功
			if(id > 0) {
				result.put("result", 1);
				result.put("msg", "添加用户成功！");
			} else { //如果添加失败
				result.put("result", 0);
				result.put("msg", "添加用户失败！请与开发人员联系。");
			}
		} catch (Exception e) {
			logger.info("ERROR:", e);
		}
		return result;
	}
	
	/**
	 * 根据ID获取一个用户并返回用户信息修改页
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/get_user_by_id")
	public String getUserById(int id, ModelMap map) {
 
		AdminUser adminUser = adminUserService.selectByPrimaryKey(id);
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(adminUser, userDTO);
		//设置该用户的角色ID到DTO
		Iterator<Role> it = adminUser.getUserRoles().iterator();
		int roleId = 0;
		if (it.hasNext()) {
			roleId = it.next().getId();
		}
		userDTO.setRoleId(roleId);
		map.put("user", userDTO);
		return "user/super_admin_edit_user_dialog";
	}
	
	/**
	 * 更新一个用户的信息，完成后返回用户列表页
	 * @param user
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/update_user")
	public String updateUser(UserDTO userDTO, ModelMap map) {
		
		AdminUser user = adminUserService.selectByPrimaryKey(userDTO.getId());
		String password = userDTO.getPassword().trim();
		//如果密码有修改
		if(password != "") {
			password = MD5Util.md5(password);
			user.setPassword(password);
		}
		//修改角色
		int roleId = userDTO.getRoleId();
		Role role = roleService.getRoleById(roleId);
		user.setUserRoles(new HashSet<Role>());
		user.getUserRoles().add(role);
		user.setUsername(userDTO.getUsername());
		user.setCompanyName(userDTO.getCompanyName());
		user.setLinkman(userDTO.getLinkman());
		user.setPhone(userDTO.getPhone());
		user.setComment(userDTO.getComment());
		int effectRows = adminUserService.updateUser(user);
		//如果修改成功
		if(effectRows > 0) {
			map.put("success", true);
			map.put("msg", "修改用户成功！");
		} else { //如果修改失败
			map.put("success", false);
			map.put("msg", "修改用户失败！请与开发人员联系。");
		}
		//返回用户列表页
		return "user/super_admin_user_manager";
	}
	
	/**
	 * 删除一个用户，完成后返回用户列表页
	 * @param user
	 * @param userType
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/delete_user")
	public String deleteUser(int id, ModelMap map) {
		
		int effectRows = adminUserService.setUserInvalid(id);
		//如果删除成功
		if(effectRows > 0) {
			map.put("success", true);
			map.put("msg", "删除用户成功！");
		} else { //如果删除失败
			map.put("success", false);
			map.put("msg", "删除用户失败！请与开发人员联系。");
		}
		//返回用户列表页
		return "user/super_admin_user_manager";
	}
	

	
	/**
	 * 密码重置
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/resetpassword", method = { RequestMethod.POST })
	public String Account_RestPassword(HttpServletRequest request) {
		String result = "{\"status\":true,\"msg\":\"重置成功\"}";
		try {
			String id = request.getParameter("id");
			String password = "123456";// request.getParameter("password");
			if (id == null || id.length() <= 0) {
				return "{\"status\":false,\"msg\":\"用户ID不能为空\"}";
			}
			if (password == null || password.length() <= 0) {
				return "{\"status\":false,\"msg\":\"新密码不能为空\"}";
			}
			AdminUser userInfo = adminUserService.selectByPrimaryKey(Integer.parseInt(id));

			if (userInfo == null) {
				return "{\"status\":false,\"msg\":\"该用户不存在\"}";
			}
			userInfo.setPassword(MD5Util.md5(password));
			if (adminUserService.updateUser(userInfo) < 1) {
				result = "{\"status\":false,\"msg\":\"重置失败\"}";
			}
		} catch (Exception ex) {
			result = "{\"status\":false,\"msg\":\"重置异常\"}";
		}
		return result;
	}
	
	@RequestMapping(value = "/passwd")
	public String passwd(String oldPassword, String password, ModelMap map) {
		if (StringUtils.isNotEmpty(password)){
			AdminUser user = getUser();
			if (user.getPassword().equals(MD5Util.md5(oldPassword))){
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
			else{
				map.put("success", false);
				map.put("message", "原密码失败！");
			}
		}
		return "user/passwd";
	}
	
	/**
	 * 修改密码
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/change_password")
	public String changePassword(Integer userId, String password, ModelMap map) {
		
		AdminUser user = new AdminUser();
		user.setId(userId);
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
		return "/common/change_password";
	}

}