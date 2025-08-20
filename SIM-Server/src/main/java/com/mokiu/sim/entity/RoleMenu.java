package com.mokiu.sim.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@TableName("role_menu")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer id;
    private Integer roleId; // 角色
    private Integer menuId; // 对应权限

}
