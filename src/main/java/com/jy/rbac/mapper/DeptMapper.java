package com.jy.rbac.mapper;

import com.github.pagehelper.Page;
import com.jy.rbac.annotation.AutoFill;
import com.jy.rbac.enumeration.OperationType;
import com.jy.rbac.pojo.entity.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DeptMapper {
    Page<Dept> getList(@Param("deptName") String deptName);

    List<Dept> getAll();

    @AutoFill(value = OperationType.INSERT)
    void add(Dept dept);

    @Update("update sys_dept set delFlag = '1', updateTime = now() where delFlag != '1' and deptId = #{deptId}")
    int remove(Long deptId);

    Dept getDeptById(Long deptId);

    @AutoFill(value = OperationType.UPDATE)
    int edit(Dept dept);

    @Select("select count(*) from sys_dept where delFlag != '1' and parentId = #{deptId}")
    Long countByParentId(Long deptId);

    @Update("update sys_dept set ancestors = concat(#{newAncestors}, substring(ancestors, char_length(#{oldAncestors}) + 1)) " +
            "where find_in_set(#{deptId}, ancestors)")
    int updateChildrenAncestors(@Param("deptId") Long deptId,
                                @Param("oldAncestors") String oldAncestors,
                                @Param("newAncestors") String newAncestors);
}
