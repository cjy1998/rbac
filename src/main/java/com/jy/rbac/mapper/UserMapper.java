package com.jy.rbac.mapper;

import com.github.pagehelper.Page;
import com.jy.rbac.annotation.AutoFill;
import com.jy.rbac.enumeration.OperationType;
import com.jy.rbac.pojo.dto.UserPageQueryDTO;
import com.jy.rbac.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @AutoFill(value = OperationType.INSERT)
    void add(User user);

    Page<User> getList(UserPageQueryDTO userPageQueryDTO);

    @Select("select userId, userName, nickName, phonenumber, email, avatar, sex, status, deptId, userType, " +
            "createBy, createTime, updateBy, updateTime, remark " +
            "from sys_user where delFlag != '1' and userId = #{id} ")
    User getUserById(Long id);

    @Select("select count(*) from sys_user where delFlag != '1' and userName = #{userName} and userId != #{userId}")
    Long countByUserName(@Param("userName") String userName, @Param("userId") Long userId);

    @Select("select count(*) from sys_user where delFlag != '1' and email = #{email} and userId != #{userId}")
    Long countByEmail(@Param("email") String email, @Param("userId") Long userId);

    @Select("select count(*) from sys_user where delFlag != '1' and deptId = #{deptId}")
    Long countByDeptId(Long deptId);

    @Update("update sys_user set delFlag = '1', updateTime = now() where delFlag != '1' and userId = #{id}")
    int delete(Long id);

    @Select("select userId, userName, nickName, phonenumber, email, avatar, sex, status, deptId, userType, " +
            "password, createBy, createTime, updateBy, updateTime, remark " +
            "from sys_user where delFlag != '1' and userName = #{userName}")
    User getUserByUserName(String userName);

    @AutoFill(value = OperationType.UPDATE)
    int edit(User user);
}
