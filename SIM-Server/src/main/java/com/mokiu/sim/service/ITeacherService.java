package com.mokiu.sim.service;

import com.mokiu.sim.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**login
 * <p>
 *  服务类
 * </p>
 *
 * @author mokiu
 * @since 2023-04-11
 */
public interface ITeacherService extends IService<Teacher> {

    Map<String, Object> login(Teacher teacher);

    Map<String, Object> getTeacherInfo(String token);

    void logout(String token);

    void addTeacher(Teacher teacher);

    Teacher getTeacherById(Integer id);

    void updateTeacher(Teacher teacher);

    void removeTeacherById(String id);
}
