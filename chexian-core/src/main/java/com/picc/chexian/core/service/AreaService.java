package com.picc.chexian.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.picc.chexian.core.entity.Area;
import com.picc.chexian.core.mapper.AreaMapper;

@Service
public class AreaService  extends BaseService {
	@Autowired
	AreaMapper mapper;
	
	public Area selectByPrimaryKey(String id){
		return mapper.selectByPrimaryKey(id);
	}
	
	public List<Area> selectByPid(String pid){
		return mapper.selectByPid(pid);
	}

	public List<Area> selectByPids(List<String> pids){
		return mapper.selectByPids(pids);
	}

}
