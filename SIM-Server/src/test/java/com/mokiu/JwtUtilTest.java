package com.mokiu;

import com.mokiu.common.utils.JwtUtil;
import com.mokiu.sim.entity.Student;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtUtilTest {
    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void testCreateJwt(){
        Student student = new Student();
        student.setId("B21031234");
        student.setName("梅老板");
        String token = jwtUtil.createToken(student);
        System.out.println(token);
    }

    @Test
    public void testParseJwt(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4OWFmZWI2MC03NmZmLTQ3NWMtOGIzOS05OTI5YzA3Zjg5MmUiLCJzdWIiOiJ7XCJpZFwiOlwiQjIxMDMxMjM0XCIsXCJuYW1lXCI6XCLmooXogIHmnb9cIn0iLCJpc3MiOiJzeXN0ZW0iLCJpYXQiOjE2OTE0MDI4MTMsImV4cCI6MTY5MTQwNDYxM30.Od0mEBYHtQTk4PJym0XGDI2qSWte5mrHlwPfSLFkIu8";
        Claims claims = jwtUtil.parseToken(token);
        System.out.println(claims);
    }

    @Test
    public void testParseJwt2(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4OWFmZWI2MC03NmZmLTQ3NWMtOGIzOS05OTI5YzA3Zjg5MmUiLCJzdWIiOiJ7XCJpZFwiOlwiQjIxMDMxMjM0XCIsXCJuYW1lXCI6XCLmooXogIHmnb9cIn0iLCJpc3MiOiJzeXN0ZW0iLCJpYXQiOjE2OTE0MDI4MTMsImV4cCI6MTY5MTQwNDYxM30.Od0mEBYHtQTk4PJym0XGDI2qSWte5mrHlwPfSLFkIu8";
        Student student = jwtUtil.parseToken(token, Student.class);
        System.out.println(student);
    }
}
