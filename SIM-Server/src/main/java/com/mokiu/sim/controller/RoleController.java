package com.mokiu.sim.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mokiu.common.vo.Result;
import com.mokiu.sim.entity.Role;
import com.mokiu.sim.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "用户身份权限管理")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @ApiOperation("获取角色列表")
    @GetMapping("/all")
    public Result<List<Role>> getAllRole(){
        List<Role> roleList = roleService.list();
        return Result.success(roleList);
    }

    @ApiOperation("查询角色信息")
    @GetMapping("/list")                       // 非必要
    public Result<Map<String, Object>> getRoleList(@RequestParam(value = "roleName", required = false) String roleName,
                                                      @RequestParam(value = "pageNo") Long pageNo,
                                                      @RequestParam(value = "pageSize") Long pageSize){
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        // 参数不为空才构造该查询条件
        wrapper.eq(StringUtils.hasLength(roleName),Role::getRoleName,roleName);

        Page<Role> page = new Page<>(pageNo, pageSize);
        roleService.page(page, wrapper);

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());

        return Result.success(data);
    }

    @ApiOperation("新增角色信息")
    @PostMapping
    public Result<?> addRole(@RequestBody Role role){
        roleService.addRole(role);
        return Result.success("新增角色信息成功");
    }

    @ApiOperation("修改角色信息")
    @PutMapping
    public Result<?> updateRole(@RequestBody Role role){
        roleService.updateRoleById(role);
        return Result.success("修改角色信息成功");
    }

    @ApiOperation("根据编号获得角色信息")
    @GetMapping("/{id}")
    public Result<Role> getRoleById(@PathVariable("id") Integer id){
        Role role = roleService.getRoleById(id);
        return Result.success(role);
    }

    @ApiOperation("删除角色信息")
    @DeleteMapping("/{id}")
    public Result<Role> deleteRoleById(@PathVariable("id") Integer id){
        roleService.removeRoleById(id);
        return Result.success("删除角色信息成功");
    }

}
