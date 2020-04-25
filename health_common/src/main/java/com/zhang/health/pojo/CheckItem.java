package com.zhang.health.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

/**
 * 检查项
 *
 * @author zhang
 */
@Data
@TableName("t_checkitem")
public class CheckItem implements Serializable {

    private static final long serialVersionUID = 1257395613227681044L;
    /**
     * 主键
     */
    @TableId
    private Integer id;

    /**
     * 项目编码
     */
    private String code;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 适用性别
     */
    private String sex;

    /**
     * 适用年龄（范围），例如：20-50
     */
    private String age;

    /**
     * 价格
     */
    private Float price;

    /**
     * 检查项类型，分为检查和检验两种类型
     */
    private String type;

    /**
     * 项目说明
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
}
