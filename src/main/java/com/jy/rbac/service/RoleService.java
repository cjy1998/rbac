package com.jy.rbac.service;

import com.jy.rbac.pojo.common.PageResult;
import com.jy.rbac.pojo.dto.RoleCreateDTO;
import com.jy.rbac.pojo.dto.RolePageQueryDTO;
import com.jy.rbac.pojo.dto.RoleUpdateDTO;
import com.jy.rbac.pojo.entity.Role;
import com.jy.rbac.pojo.vo.RoleMenuTreeVO;

public interface RoleService {
    PageResult<Role> getList(RolePageQueryDTO rolePageQueryDTO);

    void add(RoleCreateDTO roleCreateDTO);

    void remove(Long roleId);

    void edit(RoleUpdateDTO roleUpdateDTO);

    Role getRoleById(Long roleId);

    RoleMenuTreeVO getRoleMenuTree(Long roleId);
}
