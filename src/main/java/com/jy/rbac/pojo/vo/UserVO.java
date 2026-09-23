package com.jy.rbac.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
    private Long userId;
    private String  userName;
    private String  nickName;
    private String  phonenumber;
    private String  email;
    private String  avatar;
    private String  sex;
    private String  status;
    private Integer deptId;
    private String  userType;
    private String  createBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime   createTime;
    private String  updateBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime   updateTime;
    private String  remark;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Long> roleIds;
}
