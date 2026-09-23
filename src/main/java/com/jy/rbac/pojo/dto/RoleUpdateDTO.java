package com.jy.rbac.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Schema(name = "角色更新参数")
@Data
public class RoleUpdateDTO {
    @NotNull(message = "角色Id不能为空")
    private Long roleId;
    private String roleKey;
    private String roleName;
    private Integer roleSort = 0;
    private String dataScope = "2";
    private Integer deptCheckStrictly = 1;
    private Integer menuCheckStrictly = 1;
    private String  status = "0";
    private String  remark;
    private List<Long> menuIds;
}
