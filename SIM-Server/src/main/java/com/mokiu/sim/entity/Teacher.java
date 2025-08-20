package com.mokiu.sim.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author mokiu
 * @since 2023-04-11
 */
@TableName("teacher")
@Data
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String password;
    private String avatar;
    private String sex;
    private String position;
    private String academy;
    @TableLogic
    private Integer deleted;

    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Integer> roleIdList;
}
