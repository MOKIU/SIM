package com.mokiu.sim.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mokiu.common.vo.Result;
import com.mokiu.sim.entity.Student;
import com.mokiu.sim.service.IStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @since 2023-04-10
 */
@Api(tags = "学生信息接口列表")
@RestController
@RequestMapping("/admin")
public class StudentController {

    @Autowired
    private IStudentService studentService;

    @ApiOperation("获取学生列表")
    @GetMapping("/student/all")
    public Result<List<Student>> getAllStudent(){
        List<Student> list = studentService.list();
        return Result.success(list,"查询成功");
    }

    @ApiOperation("查询学生信息")
    @GetMapping("/student/list")                       // 非必要
    public Result<Map<String, Object>> getStudentList(@RequestParam(value = "id", required = false) String id,
                                                      @RequestParam(value = "name", required = false) String name,
                                                      @RequestParam(value = "academy", required = false) String academy,
                                                      @RequestParam(value = "sex", required = false) String sex,
                                                      @RequestParam(value = "pageNo") Long pageNo,
                                                      @RequestParam(value = "pageSize") Long pageSize){
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        // 参数不为空才构造该查询条件
        wrapper.eq(StringUtils.hasLength(id),Student::getId,id);
        wrapper.eq(StringUtils.hasLength(name),Student::getName,name);
        wrapper.eq(StringUtils.hasLength(academy),Student::getAcademy,academy);
        wrapper.eq(StringUtils.hasLength(sex), Student::getSex, sex);

        Page<Student> page = new Page<>(pageNo, pageSize);
        studentService.page(page, wrapper);

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());

        return Result.success(data);
    }

    @ApiOperation("新增学生信息")
    @PostMapping("/student")
    public Result<?> addStudent(@RequestBody Student student){
        studentService.save(student);
        return Result.success("新增学生信息成功");
    }

    @ApiOperation("修改学生信息")
    @PutMapping("/student")
    public Result<?> updateStudent(@RequestBody Student student){
        studentService.updateById(student);
        return Result.success("修改学生信息成功");
    }

    @ApiOperation("根据学号获得学生信息")
    @GetMapping("/student/{id}")
    public Result<Student> getStudentById(@PathVariable("id") String id){
        Student student = studentService.getById(id);
        return Result.success(student);
    }

    @ApiOperation("删除学生信息")
    @DeleteMapping("/student/{id}")
    public Result<Student> deleteStudentById(@PathVariable("id") String id){
        studentService.removeById(id);
        return Result.success("删除学生信息成功");
    }
}

