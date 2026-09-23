package com.jy.rbac.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Schema(name = "修改用户参数")
@Data
public class UserUpdateDTO {
    @NotNull(message = "用户Id必填")
    private Long    userId;
    private String  userName;
    private String  nickName;
    private String  phonenumber;
    private String  email;
    private String  avatar;
    private String  sex;
    private String  status;
    private Integer deptId;
    private String  userType;
    private String  remark;
    private List<Long> roleIds;
}
