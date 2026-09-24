package com.jy.rbac.controller;

import com.jy.rbac.pojo.common.Result;
import com.jy.rbac.pojo.dto.LoginDTO;
import com.jy.rbac.pojo.vo.LoginVO;
import com.jy.rbac.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "用户认证")
@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Operation(summary = "登录")
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginDTO loginDTO){
        LoginVO loginVO = authService.login(loginDTO);
        return Result.success(loginVO);
    }
    @Operation(summary = "退出登录")
    @PostMapping("/logout")
    public Result<String> logout(){
        authService.logout();
        return Result.success();
    }
}
