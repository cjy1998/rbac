package com.jy.rbac.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import com.jy.rbac.mapper.MenuMapper;
import com.jy.rbac.mapper.RoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class StpInterfaceImpl implements StpInterface {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        Long userId = Long.valueOf(loginId.toString());
        log.info("权限：{}",menuMapper.selectPermsByUserId(userId));
        return menuMapper.selectPermsByUserId(userId);
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        Long userId = Long.valueOf(loginId.toString());
        return roleMapper.selectRoleKeysByUserId(userId);
    }
}
