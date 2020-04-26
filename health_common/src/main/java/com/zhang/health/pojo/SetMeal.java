package com.zhang.health.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 体检套餐
 */
@Data
@TableName("t_setmeal")
public class SetMeal implements Serializable {

    @TableId
    private Integer id;
    private String name;
    private String code;
    private String helpCode;

    /**
     * 套餐适用性别：0不限 1男 2女
     */
    private String sex;

    /**
     * 套餐适用年龄
     */
    private String age;

    /**
     * 套餐价格
     */
    private Float price;
    private String remark;
    private String attention;

    /**
     * 套餐对应图片存储路径
     */
    private String img;

    /**
     * 体检套餐对应的检查组，多对多关系
     */
    @TableField(select = false)
    private List<CheckGroup> checkGroups;

    /**
     * 逻辑删除字段 ，1-删除，0-未删除
     */
    @TableLogic(value = "0")
    private Integer deleted;
}
