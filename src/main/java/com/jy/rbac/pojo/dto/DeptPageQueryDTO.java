package com.jy.rbac.pojo.dto;

import com.jy.rbac.pojo.common.PageQueryDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Schema(name = "部门列表分页查询参数")
@Data
public class DeptPageQueryDTO extends PageQueryDTO {
    public String deptName;
}
