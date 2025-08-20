package com.mokiu.sim.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mokiu.sim.entity.Menu;

import java.util.List;

public interface IMenuService extends IService<Menu> {
    List<Menu> getAllMenu();

    List<Menu> getMenuListByTeacherId(Integer teacherId);
}
