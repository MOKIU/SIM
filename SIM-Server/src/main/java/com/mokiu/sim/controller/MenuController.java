package com.mokiu.sim.controller;

import com.mokiu.common.vo.Result;
import com.mokiu.sim.entity.Menu;
import com.mokiu.sim.service.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "权限控制")
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private IMenuService menuService;

    @ApiOperation("获取权限列表")
    @GetMapping
    public Result<List<Menu>> getAllMenu(){
        List<Menu> menuList = menuService.getAllMenu();
        return Result.success(menuList);
    }
}
