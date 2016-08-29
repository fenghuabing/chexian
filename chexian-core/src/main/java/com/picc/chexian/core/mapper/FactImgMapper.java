package com.picc.chexian.core.mapper;

import java.util.List;

import com.picc.chexian.core.entity.FactImg;

public interface FactImgMapper {
    int deleteByPrimaryKey(Integer id);
    
    int deleteByFactoryId(Integer id);
    
    List<String> selectByFactoryId(Integer id);

    int insert(FactImg record);

    FactImg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FactImg record);
}