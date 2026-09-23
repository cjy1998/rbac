package com.jy.rbac.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jy.rbac.constant.MessageConstant;
import com.jy.rbac.exception.BaseException;
import com.jy.rbac.mapper.MenuMapper;
import com.jy.rbac.mapper.RoleMapper;
import com.jy.rbac.mapper.RoleMenuMapper;
import com.jy.rbac.mapper.UserRoleMapper;
import com.jy.rbac.pojo.common.PageResult;
import com.jy.rbac.pojo.dto.RoleCreateDTO;
import com.jy.rbac.pojo.dto.RolePageQueryDTO;
import com.jy.rbac.pojo.dto.RoleUpdateDTO;
import com.jy.rbac.pojo.entity.Role;
import com.jy.rbac.pojo.vo.RoleMenuTreeVO;
import com.jy.rbac.service.MenuService;
import com.jy.rbac.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private MenuService menuService;

    @Override
    public PageResult<Role> getList(RolePageQueryDTO rolePageQueryDTO) {
        PageHelper.startPage(rolePageQueryDTO.getPageNum(), rolePageQueryDTO.getPageSizeNum());
        Page<Role> roles = roleMapper.getList(rolePageQueryDTO);
        return new PageResult<Role>(roles.getTotal(), roles);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(RoleCreateDTO roleCreateDTO) {
        checkUnique(roleCreateDTO.getRoleKey(), roleCreateDTO.getRoleName(), 0L);
        Role role = new Role();
        BeanUtils.copyProperties(roleCreateDTO, role);
        roleMapper.add(role);

        /**
         * 关联菜单（新角色本身无关联，空数组无需清理）
         */
        List<Long> menuIds = roleCreateDTO.getMenuIds();
        if (menuIds != null && !menuIds.isEmpty()) {
            checkMenuIds(menuIds);
            roleMenuMapper.insertBatch(role.getRoleId(), menuIds);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long roleId) {
        Long count = userRoleMapper.countByRoleId(roleId);
        if (count != null && count > 0) {
            throw new BaseException(MessageConstant.ROLE_IN_USE);
        }
        Integer rows = roleMapper.remove(roleId);
        if (rows == 0) {
            throw new BaseException(MessageConstant.ROLE_NOT_FOUND);
        }
        roleMenuMapper.deleteByRoleId(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(RoleUpdateDTO roleUpdateDTO) {
        getRoleById(roleUpdateDTO.getRoleId());
        checkUnique(roleUpdateDTO.getRoleKey(), roleUpdateDTO.getRoleName(), roleUpdateDTO.getRoleId());
        Role role = new Role();
        BeanUtils.copyProperties(roleUpdateDTO, role);
        Integer rows = roleMapper.edit(role);
        if (rows == 0) {
            throw new BaseException(MessageConstant.ROLE_NOT_FOUND);
        }

        List<Long> menuIds = roleUpdateDTO.getMenuIds();
        if (menuIds != null) {                    // null = 本次不动关联
            roleMenuMapper.deleteByRoleId(roleUpdateDTO.getRoleId());
            if (!menuIds.isEmpty()) {             // 空数组 = 清空后不重插
                checkMenuIds(menuIds);
                roleMenuMapper.insertBatch(roleUpdateDTO.getRoleId(), menuIds);
            }
        }
    }

    @Override
    public RoleMenuTreeVO getRoleMenuTree(Long roleId) {
        getRoleById(roleId);
        RoleMenuTreeVO vo = new RoleMenuTreeVO();
        vo.setMenus(menuService.getMenuTree());
        vo.setCheckedKeys(roleMenuMapper.selectMenuIdsByRoleId(roleId));
        return vo;
    }

    private void checkUnique(String roleKey, String roleName, Long excludeRoleId) {
        if (roleMapper.countByRoleKey(roleKey, excludeRoleId) > 0) {
            throw new BaseException(MessageConstant.ROLE_KEY_EXISTS);
        }
        if (roleMapper.countByRoleName(roleName, excludeRoleId) > 0) {
            throw new BaseException(MessageConstant.ROLE_NAME_EXISTS);
        }
    }

    /**
     * 校验提交的 menuId 都真实存在（数量对不上说明有假 id 或重复 id）
     */
    private void checkMenuIds(List<Long> menuIds) {
        if (menuMapper.countByIds(menuIds) != menuIds.size()) {
            throw new BaseException(MessageConstant.MENU_NOT_FOUND);
        }
    }

    @Override
    public Role getRoleById(Long roleId) {
        Role role = roleMapper.getRoleById(roleId);
        if (role == null) {
            throw new BaseException(MessageConstant.ROLE_NOT_FOUND);
        }
        return role;
    }
}
