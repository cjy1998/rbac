package com.jy.rbac.mapper;

import com.github.pagehelper.Page;
import com.jy.rbac.annotation.AutoFill;
import com.jy.rbac.enumeration.OperationType;
import com.jy.rbac.pojo.dto.MenuPageQueryDTO;
import com.jy.rbac.pojo.entity.Menu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuMapper {
    Page<Menu> getList(MenuPageQueryDTO menuPageQueryDTO);

    List<Menu> getAll();

    @AutoFill(value = OperationType.INSERT)
    void add(Menu menu);

    @Delete("delete from sys_menu where menuId = #{menuId}")
    Integer remove(Long menuId);

    Menu getMenuById(Long menuId);

    @AutoFill(value = OperationType.UPDATE)
    Integer edit(Menu menu);

    /**
     * 校验一批 menuId 是否都真实存在（返回的数量必须等于传入数量）
     */
    Long countByIds(@Param("menuIds") List<Long> menuIds);

    /**
     * 根据userId查询对应的权限code
     * @param userId
     */
    List<String> selectPermsByUserId(Long userId);
}
