package com.jy.rbac.pojo.dto;

import com.jy.rbac.pojo.common.PageQueryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "菜单分页查询参数")
@Data
public class MenuPageQueryDTO extends PageQueryDTO {
    @Parameter(name = "菜单名称")
    private String menuName;
    private String status;
}
