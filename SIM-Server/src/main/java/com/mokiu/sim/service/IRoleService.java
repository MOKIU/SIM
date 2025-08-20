package com.mokiu.sim.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mokiu.sim.entity.Role;


public interface IRoleService extends IService<Role> {

    void addRole(Role role);

    Role getRoleById(Integer id);

    void updateRoleById(Role role);

    void removeRoleById(Integer id);
}
