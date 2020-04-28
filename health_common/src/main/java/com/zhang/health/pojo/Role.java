package com.zhang.health.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 角色
 */
@Data
@TableName("t_role")
public class Role implements Serializable {


    private static final long serialVersionUID = -3280764336208217504L;
    private Integer id;
    private String name;

    /**
     * 角色关键字，用于权限控制
     */
    private String keyword;

    /**
     * 描述
     */
    private String description;

    @TableField(select = false)
    private Set<User> users = new HashSet<User>(0);

    @TableField(select = false)
    private Set<Permission> permissions = new HashSet<Permission>(0);

    @TableField(select = false)
    private LinkedHashSet<Menu> menus = new LinkedHashSet<Menu>(0);
}
