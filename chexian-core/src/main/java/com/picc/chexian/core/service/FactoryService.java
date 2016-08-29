package com.picc.chexian.core.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.picc.chexian.core.entity.Factory;
import com.picc.chexian.core.enums.FactStatus;
import com.picc.chexian.core.mapper.FactoryMapper;

@Service
public class FactoryService  extends BaseService {
	@Autowired
	FactoryMapper mapper;
	
	public int deleteByPrimaryKey(Integer id){
		return mapper.deleteByPrimaryKey(id);
	}

	public int insert(Factory record){
		return mapper.insert(record);
	}

	public Factory selectByPrimaryKey(Integer id){
		return mapper.selectByPrimaryKey(id);
	}
	
	public List<Factory> selectByPage(String city, int start, int size, String startDate, String endDate, String name, List<FactStatus> status){
		return mapper.selectByPage(city, start, size, startDate, endDate, name, status);
	}

	public int selectCountByPage(String city, String startDate, String endDate, String name, List<FactStatus> status){
		return mapper.selectCountByPage(city, startDate, endDate, name, status);
	}
	
	public List<Factory> selectByCreator(int creator, FactStatus... status){
		return mapper.selectByCreator(creator, Arrays.asList(status));
	}


	public int updateByPrimaryKey(Factory record){
		return mapper.updateByPrimaryKeySelective(record);
	}
	
	
}
