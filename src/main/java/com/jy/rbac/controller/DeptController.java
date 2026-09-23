package com.jy.rbac.controller;

import com.jy.rbac.pojo.common.PageResult;
import com.jy.rbac.pojo.common.Result;
import com.jy.rbac.pojo.dto.DeptCreateDTO;
import com.jy.rbac.pojo.dto.DeptPageQueryDTO;
import com.jy.rbac.pojo.dto.DeptUpdateDTO;
import com.jy.rbac.pojo.vo.DeptVO;
import com.jy.rbac.service.DeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/dept")
@Tag(name = "部门管理")
public class DeptController {
    @Autowired
    private DeptService deptService;

    @Operation(summary = "部门列表")
    @GetMapping
    public Result<PageResult<DeptVO>> getList(@ParameterObject DeptPageQueryDTO deptPageQueryDTO){
        PageResult<DeptVO>  result = deptService.getList(deptPageQueryDTO);
        return Result.success(result);
    }
    @Operation(summary = "部门树列表")
    @GetMapping("/tree")
    public Result<List<DeptVO>> getTreeList(){
        List<DeptVO> tree = deptService.getTreeList();
        return Result.success(tree);
    }
    @Operation(summary = "添加部门")
    @PostMapping
    public Result<String> add(@RequestBody @Validated DeptCreateDTO deptCreateDTO){
        deptService.add(deptCreateDTO);
        return Result.success();
    }
    @Operation(summary = "删除部门")
    @DeleteMapping("/{deptId}")
    public Result<String> remove(@PathVariable Long deptId){
        deptService.remove(deptId);
        return Result.success();
    }
    @Operation(summary = "部门详情")
    @GetMapping("/{deptId}")
    public Result<DeptVO> getDeptById(@PathVariable Long deptId){
        DeptVO deptVO = deptService.getDeptById(deptId);
        return Result.success(deptVO);
    }
    @Operation(summary = "部门修改")
    @PostMapping("/update")
    public Result<String> updateDept(@RequestBody @Validated DeptUpdateDTO deptUpdateDTO){
        deptService.edit(deptUpdateDTO);
        return Result.success();
    }
}
