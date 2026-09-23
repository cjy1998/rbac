package com.jy.rbac.pojo.dto;

import com.jy.rbac.pojo.common.PageQueryDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "用户列表查询参数")
@Data
public class UserPageQueryDTO extends PageQueryDTO {

    private String userName;

    //状态 0表示禁用 1表示启用
    private String status;

    private Integer deptId;

    private String  email;
}
