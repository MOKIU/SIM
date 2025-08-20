package com.mokiu.sim.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mokiu.sim.entity.RoleMenu;

import java.util.List;

public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    public List<Integer> getMenuIdListByRoleId(Integer roleId);
}
