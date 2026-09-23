package com.jy.rbac.controller;

import com.jy.rbac.pojo.common.PageResult;
import com.jy.rbac.pojo.common.Result;
import com.jy.rbac.pojo.dto.RoleCreateDTO;
import com.jy.rbac.pojo.dto.RolePageQueryDTO;
import com.jy.rbac.pojo.dto.RoleUpdateDTO;
import com.jy.rbac.pojo.entity.Role;
import com.jy.rbac.pojo.vo.RoleMenuTreeVO;
import com.jy.rbac.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "角色管理")
@RestController
@RequestMapping("/v1/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    /**
     * 列表
     */
    @Operation(summary = "角色列表")
    @GetMapping
    public Result<PageResult<Role>> getList(@ParameterObject RolePageQueryDTO rolePageQueryDTO){
        PageResult<Role> rolePageResult = roleService.getList(rolePageQueryDTO);
        return Result.success(rolePageResult);
    }
    /**
     * 添加
     */
    @Operation(summary = "添加角色")
    @PostMapping
    public Result<String> add(@RequestBody RoleCreateDTO roleCreateDTO){
        roleService.add(roleCreateDTO);
        return Result.success();
    }
    /**
     * 删除
     */
    @Operation(summary = "删除角色")
    @DeleteMapping("/{roleId}")
    public Result<String> remove(@PathVariable Long roleId){
        roleService.remove(roleId);
        return Result.success();
    }
    /**
     * 修改
     */
    @Operation(summary = "修改角色")
    @PostMapping("/update")
    public Result<String> edit(@RequestBody RoleUpdateDTO roleUpdateDTO){
        roleService.edit(roleUpdateDTO);
        return Result.success();
    }
    /**
     * 根据Id进行查询
     */
    @Operation(summary = "角色详情")
    @GetMapping("/{roleId}")
    public Result<Role> getRoleById(@PathVariable Long roleId){
        Role role = roleService.getRoleById(roleId);
        return Result.success(role);
    }
    /**
     * 角色菜单树（分配菜单弹窗用：全量菜单树 + 该角色已勾选项）
     */
    @Operation(summary = "角色菜单树")
    @GetMapping("/{roleId}/menus")
    public Result<RoleMenuTreeVO> getRoleMenuTree(@PathVariable Long roleId){
        return Result.success(roleService.getRoleMenuTree(roleId));
    }
}
