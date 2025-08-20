package com.mokiu.sim.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@TableName("role")
@Data
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer roleId;
    private String roleName;
    private String roleDesc;

    @TableField(exist = false)
    private List<Integer> menuIdList; // 权限菜单中选中节点的key的数组
}
