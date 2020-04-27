package com.zhang.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhang.health.pojo.Member;
import com.zhang.health.pojo.Order;
import com.zhang.health.queryResult.OrderInfo;
import org.apache.ibatis.annotations.Param;


/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/25 9:51
 */
public interface OrderMapper extends BaseMapper<Order> {


    /**
     * 根据预约id查询预约相关信息,用于页面数据展示
     *
     * @param orderId 预约id
     * @return OrderInfo
     */
    OrderInfo selectOrderInfoById(@Param("orderId") Integer orderId);
}
