package com.jy.rbac.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema(name = "用户登陆参数")
@Data
public class LoginDTO {
    @NotBlank(message = "用户名不能为空")
    private String  username;
    @NotBlank(message = "用户密码不能为空")
    private String  password;
}
