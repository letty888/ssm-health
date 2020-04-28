package com.zhang.health.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 权限
 *
 * @author zhang
 */
@Data
@TableName("t_permission")
public class Permission implements Serializable {

    private static final long serialVersionUID = 3981227434560608L;

    private Integer id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限关键字，用于权限控制
     */
    private String keyword;

    /**
     * 描述
     */
    private String description;

    @TableField(select = false)
    private Set<Role> roles = new HashSet<Role>(0);

}
