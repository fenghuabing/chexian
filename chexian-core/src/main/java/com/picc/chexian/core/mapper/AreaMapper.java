package com.picc.chexian.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.picc.chexian.core.entity.Area;

public interface AreaMapper {
	Area selectByPrimaryKey(String vc2areaGuid);
    List<Area> selectByPid(String pid);
    List<Area> selectByPids(@Param("pids")List<String> pids);
}