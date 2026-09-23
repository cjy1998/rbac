package com.jy.rbac.pojo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    private Long userId;
    private String userName;
    private String nickName;
    private String password;
    private String phonenumber;
    private String email;
    private String avatar;
    private String sex;
    private String status;
    private Integer deptId;
    private String loginIP;
    private LocalDateTime loginDate;
    private String userType;
    private String remark;
}
