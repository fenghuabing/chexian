package com.picc.chexian.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.picc.chexian.core.entity.AdminUser;

@Repository
public interface AdminUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdminUser record);

    AdminUser selectByPrimaryKey(Integer id);
    
    int updateByPrimaryKeySelective(AdminUser record);

    AdminUser getUserByName(String name);
    
    int setUserInvalid(int id);
    
	List<AdminUser> selectLevel2(@Param("search") String search);

	List<AdminUser> selectLevel3(@Param("city") String city,
			@Param("search") String search);
   
}