package com.jy.rbac.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(name = "菜单创建参数")
@Data
public class MenuCreateDTO {
    @NotBlank(message = "菜单名称不能为空")
    private String menuName;
    @NotNull(message = "父级菜单不能为空")
    private Long parentId;
    private Integer orderNum;
    @NotBlank(message = "路由地址不能为空")
    private String path;
    private String component;
    private String query;
    private String isFrame;
    private String isCache;
    private String menuType;
    private String visible;
    private String status;
    private String perms;
    private String icon;
    private String remark;
}
