package com.picc.chexian.core.mapper;

import com.picc.chexian.core.entity.RoleAuthority;

public interface RoleAuthorityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleAuthority record);

    int insertSelective(RoleAuthority record);

    RoleAuthority selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleAuthority record);

    int updateByPrimaryKey(RoleAuthority record);
}