package com.jy.rbac.mapper;

import com.github.pagehelper.Page;
import com.jy.rbac.annotation.AutoFill;
import com.jy.rbac.enumeration.OperationType;
import com.jy.rbac.pojo.dto.RolePageQueryDTO;
import com.jy.rbac.pojo.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RoleMapper {
    Page<Role> getList(RolePageQueryDTO rolePageQueryDTO);
    @AutoFill(value = OperationType.INSERT)
    void add(Role role);

    @Update("update sys_role set delFlag = '1',updateTime = now() where delFlag != '1' and roleId = #{roleId}")
    Integer remove(Long roleId);

    @AutoFill(value = OperationType.UPDATE)
    Integer edit(Role role);

    Role getRoleById(Long roleId);

    @Select("select count(*) from sys_role where delFlag != '1' and roleKey = #{roleKey} and roleId != #{roleId}")
    Long countByRoleKey(@Param("roleKey") String roleKey, @Param("roleId") Long roleId);

    @Select("select count(*) from sys_role where delFlag != '1' and roleName = #{roleName} and roleId != #{roleId}")
    Long countByRoleName(@Param("roleName") String roleName, @Param("roleId") Long roleId);

    /**
     * 校验一批 roleId 是否都真实存在（返回的数量必须等于传入数量）
     */
    Long countByIds(@Param("roleIds") List<Long> roleIds);
}
