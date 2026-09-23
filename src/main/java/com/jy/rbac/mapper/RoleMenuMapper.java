package com.jy.rbac.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMenuMapper {
    int insertBatch(@Param("roleId") Long roleId, @Param("menuIds") List<Long> menuIds);

    @Delete("delete from sys_role_menu where roleId = #{roleId}")
    int deleteByRoleId(Long roleId);

    @Select("select menuId from sys_role_menu where roleId = #{roleId}")
    List<Long> selectMenuIdsByRoleId(Long roleId);

    @Select("select count(*) from sys_role_menu where menuId = #{menuId}")
    Long countByMenuId(Long menuId);
}
