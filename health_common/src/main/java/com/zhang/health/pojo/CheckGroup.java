package com.zhang.health.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 检查组
 * @author Administrator
 */
@Data
@TableName("t_checkgroup")
public class CheckGroup implements Serializable {

    private static final long serialVersionUID = 2861211508234976024L;
    /**
     * 主键
     */
    private Integer id;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 助记
     */
    private String helpCode;

    /**
     * 适用性别
     */
    private String sex;

    /**
     * 介绍
     */
    private String remark;

    /**
     * 注意事项
     */
    private String attention;

    /**
     * 逻辑删除字段 ，1-删除，0-未删除
     */
    @TableLogic(value = "0")
    private Integer deleted;

    /**
     * 一个检查组合包含多个检查项
     */
    @TableField(select = false)
    private List<CheckItem> checkItems;


}
