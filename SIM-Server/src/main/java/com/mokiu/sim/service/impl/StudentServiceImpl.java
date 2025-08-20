package com.mokiu.sim.service.impl;

import com.mokiu.sim.entity.Student;
import com.mokiu.sim.mapper.StudentMapper;
import com.mokiu.sim.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mokiu
 * @since 2023-04-10
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

}
