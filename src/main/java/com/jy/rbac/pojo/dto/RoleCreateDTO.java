package com.jy.rbac.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Schema(name = "新增角色参数")
@Data
public class RoleCreateDTO {
    @NotBlank(message = "权限字符不能为空")
    private String roleKey;
    @NotBlank(message = "角色名称不能为空")
    private String roleName;
    private Integer roleSort = 0;
    private String dataScope = "2";
    private Integer deptCheckStrictly = 1;
    private Integer menuCheckStrictly = 1;
    private String  status = "0";
    private String  remark;
    private List<Long> menuIds;
}
