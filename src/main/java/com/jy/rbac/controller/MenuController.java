package com.jy.rbac.controller;

import com.jy.rbac.pojo.common.PageResult;
import com.jy.rbac.pojo.common.Result;
import com.jy.rbac.pojo.dto.MenuCreateDTO;
import com.jy.rbac.pojo.dto.MenuPageQueryDTO;
import com.jy.rbac.pojo.dto.MenuUpdateDTO;
import com.jy.rbac.pojo.entity.Menu;
import com.jy.rbac.pojo.vo.MenuVO;
import com.jy.rbac.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "菜单管理")
@RestController
@RequestMapping("/v1/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @Operation(summary = "菜单列表")
    @GetMapping
    public Result<PageResult<Menu>> getList(@ParameterObject MenuPageQueryDTO menuPageQueryDTO){
        PageResult<Menu>  menu = menuService.getList(menuPageQueryDTO);
        return Result.success(menu);
    }
    @Operation(summary = "菜单树")
    @GetMapping("/tree")
    public Result<List<MenuVO>> getMenuTree(){
        return Result.success(menuService.getMenuTree());
    }

    @Operation(summary = "添加菜单")
    @PostMapping
    public Result<String> add(@RequestBody MenuCreateDTO menuCreateDTO){
        menuService.add(menuCreateDTO);
        return Result.success();
    }
    @Operation(summary = "删除菜单")
    @DeleteMapping("/{id}")
    public Result<String> remove(@PathVariable Long id){
        menuService.remove(id);
        return Result.success();
    }
    @Operation(summary = "根据id查询菜单")
    @GetMapping("/{id}")
    public Result<Menu> getMenuById(@PathVariable Long id){
        Menu menu = menuService.getMenuById(id);
        return Result.success(menu);
    }
    @Operation(summary = "修改菜单")
    @PostMapping("/update")
    public Result<String> edit(@RequestBody MenuUpdateDTO menuUpdateDTO){
        menuService.edit(menuUpdateDTO);
        return Result.success();
    }
    @Operation(summary = "根据roleId获取菜单树")
    @GetMapping("/roleMenuTreeselect/{roleId}")
    public Result<MenuVO> getMenuTreeByRoleId(Long roleId){
        MenuVO  menuVO = menuService.getMenuTreeByRoleId(roleId);
        return Result.success(menuVO);
    }
}
