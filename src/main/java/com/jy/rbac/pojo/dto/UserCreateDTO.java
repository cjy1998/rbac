package com.jy.rbac.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Schema(name = "新增用户参数")
@Data
public class UserCreateDTO {
    @NotBlank(message = "用户名必填")
    @Size(max = 50,message = "用户名最长50个字符")
    private String  userName;
    private String  nickName;
    private String  password;
    private String  phonenumber;
    @NotBlank(message = "邮箱必填")
    private String  email;
    private String  avatar;
    private String  sex;
    private String  status;
    private Integer deptId;
    private String  userType;
    private String  remark;
    private List<Long> roleIds;
}
