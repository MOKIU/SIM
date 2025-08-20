package com.mokiu.sim.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mokiu.sim.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    public List<Menu> getMenuListByTeacherId(@Param("teacherId") Integer teacherId,@Param("pid") Integer pid);
}
