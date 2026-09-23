package com.jy.rbac.pojo.dto;

import com.jy.rbac.pojo.common.PageQueryDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Schema(name = "角色列表查询参数")
@Data
public class RolePageQueryDTO extends PageQueryDTO {
    private String roleName;
    private String roleKey;
}
