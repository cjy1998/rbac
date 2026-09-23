package com.jy.rbac.service;

import com.jy.rbac.pojo.common.PageResult;
import com.jy.rbac.pojo.dto.MenuCreateDTO;
import com.jy.rbac.pojo.dto.MenuPageQueryDTO;
import com.jy.rbac.pojo.dto.MenuUpdateDTO;
import com.jy.rbac.pojo.entity.Menu;
import com.jy.rbac.pojo.vo.MenuVO;

import java.util.List;

public interface MenuService {
    PageResult<Menu> getList(MenuPageQueryDTO menuPageQueryDTO);

    List<MenuVO> getMenuTree();

    void add(MenuCreateDTO menuCreateDTO);

    void remove(Long id);

    Menu getMenuById(Long id);

    void edit(MenuUpdateDTO menuUpdateDTO);

    MenuVO getMenuTreeByRoleId(Long roleId);
}
