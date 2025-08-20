package com.mokiu.sim.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author mokiu
 * @since 2023-04-10
 */
@TableName("student")
public class Student implements Serializable { //实现Serializable接口,表明该类数据可以被序列化与反序列化,既可以被保存和传输

    private static final long serialVersionUID = 1L; // 指定序列化版本号

    private String id;

    private String name;

    private String sex;

    private String birthday;

    private String academy;

    @TableLogic
    private Integer deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }

    @Override
    public String toString() {
        return "Student{" +
            "id=" + id +
            ",name="+ name +
            ", sex=" + sex +
            ", birthday=" + birthday +
            ", academy=" + academy +
        "}";
    }
}
