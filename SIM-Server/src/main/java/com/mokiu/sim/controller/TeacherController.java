package com.mokiu.sim.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mokiu.common.vo.Result;
import com.mokiu.sim.entity.Teacher;
import com.mokiu.sim.service.ITeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mokiu
 * @since 2023-04-11
 */
@Api(tags = {"教师信息接口列表"})
@RestController
@RequestMapping("/admin")
public class TeacherController {

    @Autowired
    private ITeacherService teacherService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ApiOperation("教师登录")
    @PostMapping("/login")
    // 以多个键值方式返回数据，用Map类型
    public Result<Map<String, Object>> login(@RequestBody Teacher teacher){
        Map<String, Object> data =  teacherService.login(teacher);
        if (data != null){
            return Result.success(data);
        }
        return Result.fail("管理员名或密码错误!");
    }

    @ApiOperation("根据token获得教师信息")
    @GetMapping("/info")
    public Result<Map<String,Object>> getTeacherInfo(@RequestParam("token") String token){
        // 根据token获取用户信息，redis
        Map<String,Object> data = teacherService.getTeacherInfo(token);
        if(data != null){
            return Result.success(data);
        }
        // 如果没有查到登录数据，登录失效
        return Result.fail(20003,"登录信息无效，请重新登录");
    }

    @ApiOperation("教师注销登出")
    @PostMapping("/logout")
    public Result<?> logout(@RequestHeader("X-Token")  String token){
        teacherService.logout(token);
        return Result.success();
    }

    @ApiOperation("获得全部教师列表")
    @GetMapping("/teacher/all")
    public Result<List<Teacher>> getAllTeacher(){
        List<Teacher> list = teacherService.list();
        return Result.success(list,"查询成功");
    }

    @ApiOperation("查询教师信息")
    @GetMapping("/teacher/list")                             // 非必要
    public Result<Map<String, Object>> getTeacherList(@RequestParam(value = "id", required = false) String id,
                                                      @RequestParam(value = "name", required = false) String name,
                                                      @RequestParam(value = "academy", required = false) String academy,
                                                      @RequestParam(value = "sex", required = false) String sex,
                                                      @RequestParam(value = "position", required = false) String positon,
                                                      @RequestParam(value = "pageNo") Long pageNo,
                                                      @RequestParam(value = "pageSize") Long pageSize){
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        // 参数不为空才构造该查询条件
        wrapper.eq(StringUtils.hasLength(id),Teacher::getId,id);
        wrapper.eq(StringUtils.hasLength(name),Teacher::getName,name);
        wrapper.eq(StringUtils.hasLength(academy),Teacher::getAcademy,academy);
        wrapper.eq(StringUtils.hasLength(positon), Teacher::getPosition, positon);
        wrapper.eq(StringUtils.hasLength(sex), Teacher::getSex, sex);

        Page<Teacher> page = new Page<>(pageNo, pageSize);
        teacherService.page(page, wrapper);

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());

        return Result.success(data);
    }

    @ApiOperation("新增教师信息")
    @PostMapping("/teacher")
    public Result<?> addTeacher(@RequestBody Teacher teacher){
        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
        teacherService.addTeacher(teacher);
        return Result.success("新增教师信息成功");
    }

    @ApiOperation("修改教师信息")
    @PutMapping("/teacher")
    public Result<?> updateTeacher(@RequestBody Teacher teacher){
        teacher.setPassword(null);
        teacherService.updateTeacher(teacher); // 传入字段为空不更新
        return Result.success("修改教师信息成功");
    }

    @ApiOperation("根据工号获得教师信息")
    @GetMapping("/teacher/{id}")
    public Result<Teacher> getTeacherById(@PathVariable("id") Integer id){
        Teacher teacher = teacherService.getTeacherById(id);
        return Result.success(teacher);
    }

    @ApiOperation("删除教师信息")
    @DeleteMapping("/teacher/{id}")
    public Result<Teacher> deleteTeacherById(@PathVariable("id") String id){
        teacherService.removeTeacherById(id);
        return Result.success("删除教师信息成功");
    }
}