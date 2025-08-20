package com.mokiu.sim.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mokiu.sim.entity.Role;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    public List<String> getRoleNameByTeacherId(Integer teacherId);
}
