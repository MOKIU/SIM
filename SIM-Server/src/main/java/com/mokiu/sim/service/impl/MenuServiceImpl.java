package com.mokiu.sim.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mokiu.sim.entity.Menu;
import com.mokiu.sim.mapper.MenuMapper;
import com.mokiu.sim.service.IMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    // 得到所有权限菜单
    @Override
    public List<Menu> getAllMenu() {
        // 一级菜单
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getParentId, 0);
        List<Menu> menuList = this.list(wrapper);
        // 填充子菜单
        setMenuChildren(menuList);
        return menuList;
    }

    private void setMenuChildren(List<Menu> menuList) {
        if (menuList != null){
            for (Menu menu : menuList){
                LambdaQueryWrapper<Menu> subWrapper = new LambdaQueryWrapper<>();
                subWrapper.eq(Menu::getParentId, menu.getMenuId()); // 查找父节点等于一级菜单节点的list
                List<Menu> subMenuList = this.list(subWrapper);
                menu.setChildren(subMenuList);
                // 递归查询多级菜单
                setMenuChildren(subMenuList);
            }
        }
    }

    @Override
    public List<Menu> getMenuListByTeacherId(Integer teacherId) {
        // 返回一级菜单
        List<Menu> menuList = this.baseMapper.getMenuListByTeacherId(teacherId, 0);
        // 子菜单
        setMenuChildrenByTeacherId(teacherId, menuList);
        return menuList;
    }

    private void setMenuChildrenByTeacherId(Integer teacherId, List<Menu> menuList) {
        if (menuList != null){
            for (Menu menu : menuList){
                List<Menu> subMenuList = this.baseMapper.getMenuListByTeacherId(teacherId, menu.getMenuId());
                menu.setChildren(subMenuList);
                // 递归查询多级菜单
                setMenuChildrenByTeacherId(teacherId, subMenuList);
            }
        }
    }
}
