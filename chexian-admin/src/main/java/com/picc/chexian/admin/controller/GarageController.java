package com.picc.chexian.admin.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.picc.chexian.admin.dto.City;
import com.picc.chexian.admin.dto.Province;
import com.picc.chexian.admin.util.JsonTransfer;
import com.picc.chexian.core.entity.AdminUser;
import com.picc.chexian.core.entity.Area;
import com.picc.chexian.core.entity.Factory;
import com.picc.chexian.core.enums.FactQualif;
import com.picc.chexian.core.enums.FactStatus;
import com.picc.chexian.core.enums.FactType;
import com.picc.chexian.core.service.AreaService;
import com.picc.chexian.core.service.FactImgService;
import com.picc.chexian.core.service.FactoryService;

@Controller
@RequestMapping(value = "/garage")
public class GarageController extends BaseController{
    @Autowired
    private FactoryService factoryService;
    @Autowired
    private FactImgService factImgService;
	@Autowired
	private AreaService areaService;

	@RequestMapping("/main")
	public String main(ModelMap map){
		AdminUser user = getUser();
		List<Province> list = new ArrayList<Province>();
		if (user.getLevel() == 1){
			List<Area> provinces = areaService.selectByPid("0");
			List<String> pids = new ArrayList<String>();
			Map<String, Province> provinceMap = new HashMap<String, Province>();
			for (Area p: provinces){
				Province prov = new Province(p.getVc2areaGuid(), p.getVc2areaName());
				list.add(prov);
				provinceMap.put(prov.getId(), prov);
				pids.add(p.getVc2areaGuid());
			}
			List<Area> cities = areaService.selectByPids(pids);
			for (Area a: cities){
				provinceMap.get(a.getVc2pAreaGuid()).addCity(new City(a.getVc2areaGuid(), a.getVc2areaName()));
			}
		}
		else if(user.getLevel() == 2){
			Area city = areaService.selectByPrimaryKey(user.getCityId());
			Area province = areaService.selectByPrimaryKey(city.getVc2pAreaGuid());
			List<Area> cities = areaService.selectByPid(city.getVc2pAreaGuid());
			Province prov = new Province(province.getVc2areaGuid(), province.getVc2areaName());
			for (Area a: cities){
				prov.addCity(new City(a.getVc2areaGuid(), a.getVc2areaName()));
			}
			list.add(prov);
		}
		
		JSONArray array = new JSONArray();
		for (Province p: list){
			JSONObject node = new JSONObject();
			node.put("id", p.getId());
			node.put("pId", "0");
			node.put("name", p.getName());
			node.put("t", p.getName());
			node.put("click", false);
			array.add(node);
			for (City c: p.getCities()){
				JSONObject subNode = new JSONObject();
				subNode.put("id", c.getId());
				subNode.put("pId", p.getId());
				subNode.put("name", c.getName());
				subNode.put("t", c.getName());
				array.add(subNode);
			}
		}

		String tree = array.toString();
		map.put("tree", tree);
		map.put("status", FactStatus.values());
		
		return "garage/main";
	}

	@RequestMapping("/list")
	public String list(){
		return "garage/list";
	}

	@RequestMapping("/list2")
	public String list2(ModelMap map){
		AdminUser user = getUser();
		List<Province> list = new ArrayList<Province>();
		if (user.getLevel() == 1){
			List<Area> provinces = areaService.selectByPid("0");
			List<String> pids = new ArrayList<String>();
			Map<String, Province> provinceMap = new HashMap<String, Province>();
			for (Area p: provinces){
				Province prov = new Province(p.getVc2areaGuid(), p.getVc2areaName());
				list.add(prov);
				provinceMap.put(prov.getId(), prov);
				pids.add(p.getVc2areaGuid());
			}
			List<Area> cities = areaService.selectByPids(pids);
			for (Area a: cities){
				provinceMap.get(a.getVc2pAreaGuid()).addCity(new City(a.getVc2areaGuid(), a.getVc2areaName()));
			}
		}
		else if(user.getLevel() == 2){
			Area city = areaService.selectByPrimaryKey(user.getCityId());
			Area province = areaService.selectByPrimaryKey(city.getVc2pAreaGuid());
			List<Area> cities = areaService.selectByPid(city.getVc2pAreaGuid());
			Province prov = new Province(province.getVc2areaGuid(), province.getVc2areaName());
			for (Area a: cities){
				prov.addCity(new City(a.getVc2areaGuid(), a.getVc2areaName()));
			}
			list.add(prov);
		}
		
		JSONArray array = new JSONArray();
		for (Province p: list){
			JSONObject node = new JSONObject();
			node.put("id", p.getId());
			node.put("pId", "0");
			node.put("name", p.getName());
			node.put("t", p.getName());
			node.put("click", false);
			array.add(node);
			for (City c: p.getCities()){
				JSONObject subNode = new JSONObject();
				subNode.put("id", c.getId());
				subNode.put("pId", p.getId());
				subNode.put("name", c.getName());
				subNode.put("t", c.getName());
				array.add(subNode);
			}
		}

		String tree = array.toString();
		map.put("tree", tree);
		map.put("status", FactStatus.values());
		
		return "garage/list2";
	}

	@ResponseBody
	@RequestMapping("/load")
	public Object load(){
		try {
			int start = Integer.parseInt(request.getParameter("start"));// 开始记录数
			int pageSize = Integer.parseInt(request.getParameter("length"));// 每页记录数
			String sEcho = request.getParameter("draw");// 搜索内容

			String from = request.getParameter("start_date");
			String end = request.getParameter("end_date");
			String city = request.getParameter("city_id");
			String status = request.getParameter("status");
			List<FactStatus> statusList = null;
			if (status == null || status.equals("all")){
				statusList = Arrays.asList(FactStatus.values());
			}
			else{
				statusList = new ArrayList<FactStatus>();
				statusList.add(FactStatus.valueOf(status));
			}
			
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			if (StringUtils.isNotBlank(end)) {
				Date end_date = format.parse(end);
				cal.setTime(end_date);
			}
			cal.add(Calendar.DATE, 1);
			end = format.format(cal.getTime());

			String searchContent = request.getParameter("search[value]");

//			AdminUser user = getUser();
			List<Factory> list = factoryService.selectByPage(city, start, pageSize, from, end, searchContent, statusList);
			int total = factoryService.selectCountByPage(city, from, end, searchContent, statusList);

			String result = JsonTransfer.getJsonFromList(sEcho, list);
			result = "{\"draw\":" + sEcho + ",\"recordsTotal\":" + pageSize
					+ ",\"recordsFiltered\":" + total + ",\"data\":" + result + "}";
			return result;
		} catch (Exception ex) {
			return null;
		}
	}
	
	@ResponseBody
	@RequestMapping("/info")
	public ModelAndView info(){
		ModelAndView mv = new ModelAndView("garage/info");
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			Factory fac = factoryService.selectByPrimaryKey(id);
			List<String> list = factImgService.selectByFactoryId(id);
			fac.setImgFactoryList(list);
			mv.addObject("fac", fac);
			mv.addObject("category", FactType.values());
			mv.addObject("qualif", FactQualif.values());
			mv.addObject("user", getUser());			
		} catch (Exception ex) {
			return null;
		}
		return mv;
	}

	@ResponseBody
	@RequestMapping("/check")
	public Object check(){
		JSONObject result = new JSONObject();
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			int status = Integer.parseInt(request.getParameter("status"));
			Factory fac = new Factory();
			fac.setId(id);
			if(status == 1){
				fac.setStatus(FactStatus.AUDIT);
			}
			else{
				fac.setStatus(FactStatus.PENDING);
				fac.setComment(request.getParameter("comment"));
			}
			factoryService.updateByPrimaryKey(fac);
			result.put("result", "1");
			result.put("msg", "审核成功");
		} catch (Exception ex) {
			result.put("result", "0");
			result.put("msg", "审核失败");
		}
		return result;
	}

}
