package com.zhang.health.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zhang.health.annotation.ExcelAttribute;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约设置
 *
 * @author zhang
 */
@Data
@TableName("t_ordersetting")
public class OrderSetting implements Serializable {

    private static final long serialVersionUID = -2556017188233745323L;
    private Integer id;

    /**
     * 预约设置日期
     */
    @ExcelAttribute(sort = 0)
    private Date orderDate;

    /**
     * 可预约人数
     */
    @ExcelAttribute(sort = 1)
    private Integer number;

    /**
     * 已预约人数
     */
    private Integer reservations;
}
