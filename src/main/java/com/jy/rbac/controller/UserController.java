package com.jy.rbac.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.jy.rbac.pojo.common.PageResult;
import com.jy.rbac.pojo.common.Result;
import com.jy.rbac.pojo.dto.UserCreateDTO;
import com.jy.rbac.pojo.dto.UserPageQueryDTO;
import com.jy.rbac.pojo.dto.UserUpdateDTO;
import com.jy.rbac.pojo.vo.UserVO;
import com.jy.rbac.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/user")
@Tag(name = "用户管理")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "用户列表")
    @SaCheckPermission("system:user:list")
    @GetMapping
    public Result<PageResult<UserVO>> getList(@ParameterObject UserPageQueryDTO userPageQueryDTO){
        PageResult<UserVO> userVOPageResult = userService.getList(userPageQueryDTO);
        return Result.success(userVOPageResult);
    }
    @Operation(summary = "新增用户")
    @SaCheckPermission("system:user:add")
    @PostMapping
    public Result<String> add(@RequestBody @Validated UserCreateDTO userCreateDTO){
        userService.add(userCreateDTO);
        return Result.success();
    }
    @Operation(summary = "用户详情")
    @SaCheckPermission("system:user:query")
    @GetMapping("/{id}")
    public Result<UserVO> getUserById(@PathVariable Long id){
        UserVO  userVO = userService.getUserById(id);
        return Result.success(userVO);
    }
    @Operation(summary = "删除用户")
    @SaCheckPermission("system:user:remove")
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id){
        userService.delete(id);
        return Result.success();
    }
    @Operation(summary = "修改用户")
    @SaCheckPermission("system:user:edit")
    @PostMapping("/update")
    public Result<String> update(@RequestBody @Validated UserUpdateDTO userUpdateDTO){
        userService.update(userUpdateDTO);
        return Result.success();
    }
}
