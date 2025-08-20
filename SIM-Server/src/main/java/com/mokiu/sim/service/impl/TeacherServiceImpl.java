package com.mokiu.sim.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mokiu.common.utils.JwtUtil;
import com.mokiu.sim.entity.Menu;
import com.mokiu.sim.entity.Teacher;
import com.mokiu.sim.entity.TeacherRole;
import com.mokiu.sim.mapper.RoleMapper;
import com.mokiu.sim.mapper.TeacherMapper;
import com.mokiu.sim.mapper.TeacherRoleMapper;
import com.mokiu.sim.service.IMenuService;
import com.mokiu.sim.service.ITeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mokiu
 * @since 2023-04-11
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

    // 不再使用redis
    /*@Autowired
    private RedisTemplate redisTemplate;*/

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Resource
    private TeacherRoleMapper teacherRoleMapper;

    @Resource
    private RoleMapper roleMapper;

    @Autowired
    private IMenuService menuService;

    @Override
    public Map<String, Object> login(Teacher teacher) {
        // 根据用户名查询
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Teacher::getName, teacher.getName());
        Teacher loginTeacher = this.baseMapper.selectOne(wrapper);
        // 结果不为空，并且密码和传入密码匹配，则生成token，并将用户信息存入redis
        if(loginTeacher != null && passwordEncoder.matches(teacher.getPassword(), loginTeacher.getPassword())){
            // 暂时用UUID, 终极方案是jwt
            // String key = "admin:" + UUID.randomUUID();

            // 存入redis
            loginTeacher.setPassword(null);
            // redisTemplate.opsForValue().set(key,loginTeacher,30, TimeUnit.MINUTES);
            // jwt生成token
            String token = jwtUtil.createToken(loginTeacher);

            // 返回数据
            Map<String, Object> data = new HashMap<>();
            data.put("token",token);
            return data;
        }
        return null;
    }

    @Override
    public Map<String, Object> getTeacherInfo(String token) {
        // 根据token获取管理员信息，redis
        // Object obj = redisTemplate.opsForValue().get(token);
        Teacher loginTeacher = null;
        try {
            loginTeacher = jwtUtil.parseToken(token, Teacher.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(loginTeacher != null){
            // 添加教师基本信息
            // Admin loginTeacher = JSON.parseObject(JSON.toJSONString(obj),Admin.class); 从redis反序列化得到数据
            Map<String, Object> data = new HashMap<>();
            data.put("id",loginTeacher.getId());
            data.put("avatar",loginTeacher.getAvatar());

            // 角色
            List<String> roleList = roleMapper.getRoleNameByTeacherId(loginTeacher.getId());
            data.put("roles", roleList);

            // 权限列表
            List<Menu> menuList = menuService.getMenuListByTeacherId(loginTeacher.getId());
            data.put("menuList", menuList);
            
            return data;
        }
        return null;
    }

    // 根据token从redis删掉该登录用户
    @Override
    public void logout(String token) {
        //redisTemplate.delete(token);
    }

    @Transactional
    @Override
    public void addTeacher(Teacher teacher) {
        // 插入角色表
        this.baseMapper.insert(teacher);
        // 插入教师角色表
        List<Integer> roleIdList = teacher.getRoleIdList();
        if (roleIdList != null){
            for (Integer roleId: roleIdList){
                teacherRoleMapper.insert(new TeacherRole(null, teacher.getId(), roleId));
            }
        }
    }

    @Override
    public Teacher getTeacherById(Integer id) {
        // 查询到该教师
        Teacher teacher = this.baseMapper.selectById(id);
        LambdaQueryWrapper<TeacherRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TeacherRole::getTeacherId, teacher.getId());
        List<TeacherRole> teacherRoleList = teacherRoleMapper.selectList(wrapper);
        // map遍历每个元素,返回roleId,最后收集一下
        List<Integer> roleIdList = teacherRoleList.stream()
                                                  .map(teacherRole -> {return teacherRole.getRoleId();})
                                                  .collect(Collectors.toList());
        teacher.setRoleIdList(roleIdList);
        return teacher;
    }

    @Transactional
    @Override
    public void updateTeacher(Teacher teacher) {
        // 更新教师表
        this.baseMapper.updateById(teacher);
        // 清除原有角色
        /*teacherRoleMapper.deleteById(teacher); // 根据teacherRole主键删除,但该teacherRole的主键不是teacherId,此方法不行*/
        LambdaQueryWrapper<TeacherRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TeacherRole::getTeacherId, teacher.getId());
        teacherRoleMapper.delete(wrapper);
        // 设置新的角色
        List<Integer> roleIdList = teacher.getRoleIdList();
        if (roleIdList != null){
            for (Integer roleId: roleIdList){
                teacherRoleMapper.insert(new TeacherRole(null, teacher.getId(), roleId));
            }
        }
    }

    @Transactional
    @Override
    public void removeTeacherById(String id) {
        // 删除教师表
        this.baseMapper.deleteById(id);
        // 删除角色教师表
        LambdaQueryWrapper<TeacherRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TeacherRole::getTeacherId, id);
        teacherRoleMapper.delete(wrapper);
    }
}
