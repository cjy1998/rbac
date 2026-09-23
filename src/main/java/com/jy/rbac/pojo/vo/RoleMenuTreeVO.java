package com.jy.rbac.pojo.vo;

import lombok.Data;

import java.util.List;

@Data
public class RoleMenuTreeVO {
    /**
     * 全量菜单树
     */
    private List<MenuVO> menus;
    /**
     * 该角色已勾选的菜单Id集合
     */
    private List<Long> checkedKeys;
}
