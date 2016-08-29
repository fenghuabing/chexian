package com.picc.chexian.weixin.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.picc.chexian.core.entity.AdminUser;
import com.picc.chexian.core.entity.Area;
import com.picc.chexian.core.entity.Factory;
import com.picc.chexian.core.enums.FactQualif;
import com.picc.chexian.core.enums.FactStatus;
import com.picc.chexian.core.enums.FactType;
import com.picc.chexian.core.service.AdminUserService;
import com.picc.chexian.core.service.AreaService;
import com.picc.chexian.core.service.FactImgService;
import com.picc.chexian.core.service.FactoryService;

/**
 * @author sxy 2015年4月20日 下午5:00:03
 * 
 */
@Controller
public class FactoryContrller extends BaseController {
	public final static String USER_SESSION_KEY = "user_session_key";

	@Autowired
	AdminUserService adminUserService;
	@Autowired
	FactoryService facService;
	@Autowired
	FactImgService factImgService;
	@Autowired
	AreaService areaService;
	

	@RequestMapping(value = "/index")
	public ModelAndView index(Principal principal) {
		String name = principal.getName();
		AdminUser user = adminUserService.getUserByName(name);
		request.getSession().setAttribute(USER_SESSION_KEY, user);
		
		ModelAndView mv = new ModelAndView("index");
		List<Factory> all = facService.selectByCreator(user.getId(), FactStatus.SUBMIT, FactStatus.AUDIT, FactStatus.PENDING);
		List<Factory> submit = new ArrayList<Factory>();
		List<Factory> pending = new ArrayList<Factory>();
		for (Factory fac: all){
			if (fac.getStatus() == FactStatus.SUBMIT)
				submit.add(fac);
			else if (fac.getStatus() == FactStatus.PENDING)
				pending.add(fac);
		}
		mv.addObject("all", all);
		mv.addObject("submit", submit);
		mv.addObject("pending", pending);
		return mv;
	}

	@RequestMapping(value = "/input")
	public ModelAndView input(String id,Principal principal){
		String name = principal.getName();
		AdminUser user = adminUserService.getUserByName(name);
		Area city = areaService.selectByPrimaryKey(user.getCityId());
		
		ModelAndView mv = new ModelAndView("input");
		int ID = NumberUtils.toInt(id);
		if (ID > 0){
			Factory fac = facService.selectByPrimaryKey(ID);
			List<String> list = factImgService.selectByFactoryId(fac.getId());
			fac.setImgFactoryList(list);
			String imgFactory = "";
			for(String item: list){
				imgFactory = imgFactory+","+item;
			}
			if(imgFactory.length()>0){
				imgFactory = imgFactory.substring(1);
			}
			fac.setImgFactory(imgFactory);
			mv.addObject("fac", fac);
			Area province = areaService.selectByPrimaryKey(fac.getProvince());
			mv.addObject("province", province);
			mv.addObject("city", city);
		}
		else{
			Area province = areaService.selectByPrimaryKey(city.getVc2pAreaGuid());
			mv.addObject("city", city);
			mv.addObject("province", province);
		}
		List<Area> districts = areaService.selectByPid(city.getVc2areaGuid());
		mv.addObject("districts", districts);
		mv.addObject("category", FactType.values());
		mv.addObject("qualif", FactQualif.values());
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/add")
	public Object add(Factory f,Principal principal){
		AdminUser user = adminUserService.getUserByName(principal.getName());
		if (f.getId()==null){
			f.setCreateTime(new Date());
			f.setCreator(user.getId());
			f.setStatus(FactStatus.SUBMIT);
			facService.insert(f);
		}
		else{
			facService.updateByPrimaryKey(f);
		}
		
		String imgs = f.getImgFactory();
		String [] imgArray = imgs.split(",");
		
		factImgService.deleteByFactoryId(f.getId());
		for (String img: imgArray){
			if (StringUtils.isNotBlank(img)){
				factImgService.insert(f.getId(), img);
			}
		}
		JSONObject result = new JSONObject();
		result.put("result", "1");
		result.put("id", f.getId());
		result.put("msg", "添加成功");
		return result;
	}
	
	@RequestMapping(value = "/map")
	public ModelAndView map(String id){
		ModelAndView mv = new ModelAndView("map");
		int ID = NumberUtils.toInt(id);
		if (ID > 0){
			Factory fac = facService.selectByPrimaryKey(ID);
			Area city = areaService.selectByPrimaryKey(fac.getCity());
			mv.addObject("fac", fac);
			mv.addObject("city", city);
		}
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/location")
	public Object location(int id, double lon, double lat){
		JSONObject result = new JSONObject();
		Factory f = facService.selectByPrimaryKey(id);
		if (f.getStatus()==FactStatus.AUDIT){
			result.put("result", "0");
			result.put("msg", "不能标注");
			return result;
		}
		f = new Factory();
		f.setId(id);
		f.setLon(lon);
		f.setLat(lat);
		facService.updateByPrimaryKey(f);
		result.put("result", "1");
		result.put("id", f.getId());
		result.put("msg", "地图标注成功");
		return result;
	}

}
