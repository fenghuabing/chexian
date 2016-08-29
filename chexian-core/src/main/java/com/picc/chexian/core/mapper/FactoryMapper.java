package com.picc.chexian.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.picc.chexian.core.entity.Factory;
import com.picc.chexian.core.enums.FactStatus;

public interface FactoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Factory record);

    Factory selectByPrimaryKey(Integer id);
    
	List<Factory> selectByPage(
			@Param("city") String city, 
			@Param("start") int start,
			@Param("size") int size, 
			@Param("startDate") String startDate,
			@Param("endDate") String endDate, 
			@Param("name") String name,
			@Param("status") List<FactStatus> status);

	int selectCountByPage(
			@Param("city") String city, 
			@Param("startDate") String startDate,
			@Param("endDate") String endDate, 
			@Param("name") String name,
			@Param("status") List<FactStatus> status);
	
	List<Factory> selectByCreator(@Param("creator") int creator,
			@Param("status") List<FactStatus> status);

	int updateByPrimaryKeySelective(Factory record);
}