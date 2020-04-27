package com.zhang.health.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检预约信息
 *
 * @author zhang
 */
@Data
@TableName("t_order")
public class Order implements Serializable {

    public static final String ORDER_TYPE_TELEPHONE = "电话预约";
    public static final String ORDER_TYPE_WEI_XIN = "微信预约";
    public static final String ORDER_STATUS_YES = "已到诊";
    public static final String ORDER_STATUS_NO = "未到诊";
    private static final long serialVersionUID = 5327946117450614854L;
    private Integer id;

    /**
     * 会员id
     */
    private Integer memberId;

    /**
     * 预约日期
     */
    private Date orderDate;

    /**
     * 预约类型 电话预约/微信预约
     */
    private String orderType;

    /**
     * 预约状态（是否到诊）
     */
    private String orderStatus;

    /**
     * 体检套餐id
     */
    private Integer setmealId;

    public Order() {
    }

    public Order(Integer id) {
        this.id = id;
    }

    public Order(Integer memberId, Date orderDate, String orderType, String orderStatus, Integer setmealId) {
        this.memberId = memberId;
        this.orderDate = orderDate;
        this.orderType = orderType;
        this.orderStatus = orderStatus;
        this.setmealId = setmealId;
    }

    public Order(Integer id, Integer memberId, Date orderDate, String orderType, String orderStatus, Integer setmealId) {
        this.id = id;
        this.memberId = memberId;
        this.orderDate = orderDate;
        this.orderType = orderType;
        this.orderStatus = orderStatus;
        this.setmealId = setmealId;
    }
}
