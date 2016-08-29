package com.picc.chexian.core.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.picc.chexian.core.entity.FactImg;
import com.picc.chexian.core.mapper.FactImgMapper;

@Service
public class FactImgService  extends BaseService {
	@Autowired
	FactImgMapper mapper;
	
    public int deleteByPrimaryKey(Integer id){
    	return mapper.deleteByPrimaryKey(id);
    }

    public int deleteByFactoryId(Integer id){
    	return mapper.deleteByFactoryId(id);
    }
    
    public List<String> selectByFactoryId(Integer id){
    	return mapper.selectByFactoryId(id);
    }

    public int insert(int factId, String path){
    	FactImg record = new FactImg();
    	record.setFactId(factId);
    	record.setPath(path);
    	record.setStatus(0);
    	record.setCreateTime(new Date());
    	return insert(record);
    }

    public int insert(FactImg record){
    	return mapper.insert(record);
    }

    public FactImg selectByPrimaryKey(Integer id){
    	return mapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKey(FactImg record){
    	return mapper.updateByPrimaryKeySelective(record);
    }

	
	
}
