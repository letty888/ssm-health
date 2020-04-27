package com.zhang.health.queryResult;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/27 18:34
 */
@Data
public class OrderInfo implements Serializable {
    private static final long serialVersionUID = 3072719861791876891L;


   /* <p>体检人：{{orderInfo.memberName}}</p>
    <p>体检套餐：{{orderInfo.setMealName}}</p>
    <p>体检日期：{{orderInfo.orderDate}}</p>
    <p>预约类型：{{orderInfo.orderType}}</p>*/

    private String memberName;
    private String setMealName;
    private String orderDate;
    private String orderType;
    private String idCard;
    private String phoneNumber;
}
