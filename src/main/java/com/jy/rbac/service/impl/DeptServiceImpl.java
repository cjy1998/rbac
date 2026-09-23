package com.jy.rbac.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jy.rbac.constant.MessageConstant;
import com.jy.rbac.exception.BaseException;
import com.jy.rbac.mapper.DeptMapper;
import com.jy.rbac.mapper.UserMapper;
import com.jy.rbac.pojo.common.PageResult;
import com.jy.rbac.pojo.dto.DeptCreateDTO;
import com.jy.rbac.pojo.dto.DeptPageQueryDTO;
import com.jy.rbac.pojo.dto.DeptUpdateDTO;
import com.jy.rbac.pojo.entity.Dept;
import com.jy.rbac.pojo.vo.DeptVO;
import com.jy.rbac.service.DeptService;
import com.jy.rbac.utils.TreeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public PageResult<DeptVO> getList(DeptPageQueryDTO deptPageQueryDTO) {
        PageHelper.startPage(deptPageQueryDTO.getPageNum(), deptPageQueryDTO.getPageSizeNum());
        Page<Dept> depts = deptMapper.getList(deptPageQueryDTO.getDeptName());
        List<DeptVO> rows = depts.getResult().stream()
                .map(this::toDeptVO)
                .toList();
        return new PageResult<DeptVO>(depts.getTotal(), rows);
    }

    @Override
    public List<DeptVO> getTreeList() {
        List<Dept> depts = deptMapper.getAll();
        List<DeptVO> vos = depts.stream()
                .map(this::toDeptVO)
                .toList();
        return TreeUtils.buildDeptTree(vos);
    }

    @Override
    public void add(DeptCreateDTO deptCreateDTO) {
        Dept dept = new Dept();
        BeanUtils.copyProperties(deptCreateDTO, dept);
        dept.setAncestors(buildAncestors(deptCreateDTO.getParentId()));
        deptMapper.add(dept);
    }

    private String buildAncestors(Long parentId) {
        if (parentId == 0L) {
            return "0";
        }
        DeptVO parent = getDeptById(parentId);
        return parent.getAncestors() + "," + parent.getDeptId();
    }

    @Override
    public void remove(Long deptId) {
        Long childCount = deptMapper.countByParentId(deptId);
        if (childCount > 0){
            throw new BaseException(MessageConstant.EXIST_CHILD_DEPT);
        }
        Long userCount = userMapper.countByDeptId(deptId);
        if (userCount > 0) {
            throw new BaseException(MessageConstant.DEPT_EXIST_USER);
        }
        int rows = deptMapper.remove(deptId);
        if (rows == 0 ){
            throw new BaseException(MessageConstant.DEPT_NOT_FOUND);
        }
    }

    @Override
    public DeptVO getDeptById(Long deptId) {
        Dept dept = deptMapper.getDeptById(deptId);
        if (dept != null){
            DeptVO deptVO = new DeptVO();
            BeanUtils.copyProperties(dept,deptVO);
            return deptVO;
        }
        throw new BaseException(MessageConstant.DEPT_NOT_FOUND);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(DeptUpdateDTO deptUpdateDTO) {
        DeptVO old = getDeptById(deptUpdateDTO.getDeptId());
        Dept dept = new Dept();
        BeanUtils.copyProperties(deptUpdateDTO, dept);
        if (!deptUpdateDTO.getParentId().equals(old.getParentId())) {
            String newAncestors = buildAncestors(deptUpdateDTO.getParentId());
            String chain = "," + newAncestors + ",";
            if (chain.contains("," + deptUpdateDTO.getDeptId() + ",")) {
                throw new BaseException(MessageConstant.DEPT_PARENT_INVALID);
            }
            if (old.getAncestors() != null) {
                deptMapper.updateChildrenAncestors(deptUpdateDTO.getDeptId(), old.getAncestors(), newAncestors);
            }
            dept.setAncestors(newAncestors);
        }
        int rows = deptMapper.edit(dept);
        if (rows == 0) {
            throw new BaseException(MessageConstant.DEPT_NOT_FOUND);
        }
    }

    private DeptVO toDeptVO(Dept dept) {
        DeptVO vo = new DeptVO();
        BeanUtils.copyProperties(dept, vo);
        return vo;
    }
}
