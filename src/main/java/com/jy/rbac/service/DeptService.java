package com.jy.rbac.service;

import com.jy.rbac.pojo.common.PageResult;
import com.jy.rbac.pojo.dto.DeptCreateDTO;
import com.jy.rbac.pojo.dto.DeptPageQueryDTO;
import com.jy.rbac.pojo.dto.DeptUpdateDTO;
import com.jy.rbac.pojo.vo.DeptVO;

import java.util.List;

public interface DeptService {
    PageResult<DeptVO> getList(DeptPageQueryDTO deptPageQueryDTO);

    List<DeptVO> getTreeList();

    void add(DeptCreateDTO deptCreateDTO);

    void remove(Long deptId);

    DeptVO getDeptById(Long deptId);

    void edit(DeptUpdateDTO deptUpdateDTO);
}
