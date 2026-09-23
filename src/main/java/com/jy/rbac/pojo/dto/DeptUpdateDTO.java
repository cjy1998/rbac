package com.jy.rbac.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Schema(name = "修改部门参数")
@Data
public class DeptUpdateDTO {
    @NotNull(message = "部门id不能为空")
    private Long deptId;
    @NotNull(message = "父id不能为空")
    private Long parentId;
    private String ancestors;
    @NotBlank(message = "部门名称不能为空")
    private String deptName;
    private Integer orderNum;
    private String leader;
    private String phone;
    private String email;
    private String status;
}
