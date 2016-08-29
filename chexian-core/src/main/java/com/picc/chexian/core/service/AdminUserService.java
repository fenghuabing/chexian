package com.picc.chexian.core.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.picc.chexian.core.entity.AdminUser;
import com.picc.chexian.core.entity.UserRole;
import com.picc.chexian.core.mapper.AdminUserMapper;
import com.picc.chexian.core.mapper.UserRoleMapper;

@Service
public class AdminUserService extends BaseService {
    @Autowired
    private AdminUserMapper adminUserMapper;
    
    @Autowired
    private UserRoleMapper userRoleMapper;
    
    public int deleteByPrimaryKey(Integer id){
    	return adminUserMapper.deleteByPrimaryKey(id);
    }
    
    public AdminUser selectByPrimaryKey(Integer id){
    	return adminUserMapper.selectByPrimaryKey(id);
    }
    
	public AdminUser getUserByName(String name) {
		return adminUserMapper.getUserByName(name);
	}

    public List<AdminUser> selectLevel2(String search){
    	return adminUserMapper.selectLevel2(search);
    }

    public List<AdminUser> selectLevel3(String city, String search){
    	return adminUserMapper.selectLevel3(city, search);
    }

    public int addUser(AdminUser record){
    	adminUserMapper.insert(record);
    	UserRole ur = new UserRole();
    	ur.setCreateTime(new Date());
    	ur.setModifyTime(new Date());
    	ur.setUserId(record.getId());
    	ur.setRoleId((int)record.getLevel());
    	userRoleMapper.insert(ur);
		return record.getId();
    }

    public int updateUser(AdminUser record){
    	return adminUserMapper.updateByPrimaryKeySelective(record);
    }
    
	public int setUserInvalid(int id) {
		return adminUserMapper.setUserInvalid(id);
	}
	
}