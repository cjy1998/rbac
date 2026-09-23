package com.jy.rbac.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserRoleMapper {
    int insertBatch(@Param("userId") Long userId, @Param("roleIds")List<Long> roleIds);

    @Delete("delete from sys_user_role where userId = #{userId}")
    int deleteByUserId(Long userId);

    @Select("select roleId from sys_user_role where userId = #{userId}")
    List<Long> selectRoleIdsByUserId(Long userId);

    @Select("select count(*) from sys_user_role where roleId = #{roleId}")
    Long countByRoleId(Long roleId);
}
