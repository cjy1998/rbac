package com.jy.rbac.pojo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity{
    private Long roleId;
    private String roleKey;
    private String roleName;
    private Integer roleSort;
    private String dataScope;
    private Integer deptCheckStrictly;
    private Integer menuCheckStrictly;
    private String  status;
    private String  remark;
}
