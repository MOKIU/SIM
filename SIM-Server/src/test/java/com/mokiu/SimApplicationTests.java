package com.mokiu;

import com.mokiu.sim.entity.Student;
import com.mokiu.sim.mapper.StudentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class SimApplicationTests {

    @Resource
    private StudentMapper studentMapper;

    @Test
    void testMapper() {
        List<Student> students = studentMapper.selectList(null);
        students.forEach(System.out::println);
    }

}
