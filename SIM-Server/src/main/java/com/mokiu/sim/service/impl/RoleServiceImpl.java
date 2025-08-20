package com.mokiu.sim.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mokiu.sim.entity.Role;
import com.mokiu.sim.entity.RoleMenu;
import com.mokiu.sim.mapper.RoleMapper;
import com.mokiu.sim.mapper.RoleMenuMapper;
import com.mokiu.sim.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Override
    @Transactional // 涉及多表的操作,使用该注解可以明确定义事务的范围和边界,以实现良好的事务管理和控制。
    public void addRole(Role role) { // 新增权限
        // 将新增角色写入角色表
        this.baseMapper.insert(role);
        // 将新增角色写入角色与权限菜单对应表
        if (role.getMenuIdList() != null){
            for(Integer menuId : role.getMenuIdList()){
                roleMenuMapper.insert(new RoleMenu(null, role.getRoleId(), menuId));
            }
        }
    }

    @Override
    public Role getRoleById(Integer id) {
        // 查询角色原始信息
        Role role = this.baseMapper.selectById(id);
        // 根据角色Id获得他的权限列表传给前端显示
        List<Integer> MenuIdList = roleMenuMapper.getMenuIdListByRoleId(id);
        role.setMenuIdList(MenuIdList);
        return role;
    }

    // 分为两部分：修改原始信息,修改权限信息
    @Transactional // 操作role和role_menu两表
    @Override
    public void updateRoleById(Role role) {
        // 更新角色表的原始信息(姓名,角色描述)
        this.baseMapper.updateById(role);
        // 删除原来的权限
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId, role.getRoleId());
        roleMenuMapper.delete(wrapper);
        // 更新为更改后的权限
        if (role.getMenuIdList() != null){
            for(Integer menuId : role.getMenuIdList()){
                roleMenuMapper.insert(new RoleMenu(null, role.getRoleId(), menuId));
            }
        }
    }

    @Transactional
    @Override
    public void removeRoleById(Integer id) {
        // 删除角色列表(role)信息
        this.baseMapper.deleteById(id);
        // 删除角色权限列表(role_menu)信息
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId, id);
        roleMenuMapper.delete(wrapper);
    }
}
