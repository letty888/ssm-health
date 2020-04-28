package com.zhang.health.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.*;

/**
 * 菜单
 *
 * @author zhang
 */
@Data
@TableName("t_menu")
public class Menu implements Serializable {
    private static final long serialVersionUID = 2083640593148784658L;
    private Integer id;
    private String name; // 菜单名称
    private String linkUrl; // 访问路径
    private String path;//菜单项所对应的路由路径
    private Integer priority; // 优先级（用于排序）
    private String description; // 描述
    private String icon;//图标
    @TableField(select = false)
    private Set<Role> roles = new HashSet<Role>(0);//角色集合

    @TableField(select = false)
    private List<Menu> children = new ArrayList<>();//子菜单集合
    private Integer parentMenuId;//父菜单id
}
