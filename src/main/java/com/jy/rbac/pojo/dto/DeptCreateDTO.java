package com.jy.rbac.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(name = "新增部门参数")
@Data
public class DeptCreateDTO {
    @NotNull(message = "父id不能为空")
    private Long parentId;
    @NotBlank(message = "部门名称不能为空")
    private String deptName;
    private Integer orderNum = 0;
    private String leader;
    private String phone;
    private String email;
    private String status = "0";
}
