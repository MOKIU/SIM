package com.mokiu.sim.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@TableName("menu")
@Data
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "menu_id", type = IdType.AUTO)
    private Integer menuId;
    private String component;
    private String path;
    private String name;
    private String title;
    private String icon;
    private Integer parentId;
    private String isLeaf;
    private Boolean hidden;

    @TableField(exist = false) // 表中不存在,设不存在
    @JsonInclude(JsonInclude.Include.NON_EMPTY) // 如果数据为空,则返回的json中无此数据
    private List<Menu> children; // 如果不处理，返回null，会影响动态路由

    @TableField(exist = false)
    private Map<String, Object> meta; // 元数据 用于保存一些额外的信息
    public Map<String, Object> getMeta(){
        meta = new HashMap<>();
        meta.put("title", this.title);
        meta.put("icon", this.icon);
        return this.meta;
    }
}
