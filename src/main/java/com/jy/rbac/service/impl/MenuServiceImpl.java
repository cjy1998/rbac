package com.jy.rbac.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jy.rbac.constant.MessageConstant;
import com.jy.rbac.exception.BaseException;
import com.jy.rbac.mapper.MenuMapper;
import com.jy.rbac.mapper.RoleMenuMapper;
import com.jy.rbac.pojo.common.PageResult;
import com.jy.rbac.pojo.dto.MenuCreateDTO;
import com.jy.rbac.pojo.dto.MenuPageQueryDTO;
import com.jy.rbac.pojo.dto.MenuUpdateDTO;
import com.jy.rbac.pojo.entity.Menu;
import com.jy.rbac.pojo.vo.MenuVO;
import com.jy.rbac.service.MenuService;
import com.jy.rbac.utils.TreeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public PageResult<Menu> getList(MenuPageQueryDTO menuPageQueryDTO) {
        PageHelper.startPage(menuPageQueryDTO.getPageNum(), menuPageQueryDTO.getPageSizeNum());
        Page<Menu> menus = menuMapper.getList(menuPageQueryDTO);
        return new PageResult<Menu>(menus.getTotal(),menus);
    }

    @Override
    public void add(MenuCreateDTO menuCreateDTO) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuCreateDTO,menu);
        menuMapper.add(menu);
    }

    @Override
    public List<MenuVO> getMenuTree() {
        List<Menu> menus = menuMapper.getAll();
        List<MenuVO> voList = menus.stream().map(menu -> {
            MenuVO vo = new MenuVO();
            BeanUtils.copyProperties(menu, vo);
            return vo;
        }).toList();
        return TreeUtils.buildMenuTree(voList);
    }

    @Override
    public void remove(Long id) {
        Long count = roleMenuMapper.countByMenuId(id);
        if (count != null && count > 0) {
            throw new BaseException(MessageConstant.MENU_IN_USE);
        }
        Integer row = menuMapper.remove(id);
        if (row == 0) {
            throw new BaseException(MessageConstant.MENU_NOT_FOUND);
        }
    }

    @Override
    public Menu getMenuById(Long id) {
        Menu  menu = menuMapper.getMenuById(id);
        if (menu == null){
            throw new BaseException(MessageConstant.MENU_NOT_FOUND);
        }
        return menu;
    }

    @Override
    public void edit(MenuUpdateDTO menuUpdateDTO) {
        getMenuById(menuUpdateDTO.getMenuId());
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuUpdateDTO,menu);
        Integer row = menuMapper.edit(menu);
        if (row == 0) {
            throw new BaseException(MessageConstant.MENU_NOT_FOUND);
        }
    }

    @Override
    public MenuVO getMenuTreeByRoleId(Long roleId) {
        return null;
    }
}
