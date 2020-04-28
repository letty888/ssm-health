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

    /**
     * 查询今日新增会员数
     *
     * @param reportDate 当前日期
     * @return Integer 今日新增会员数
     */
    Integer findTodayOrderNumber(@Param("reportDate") String reportDate);

    /**
     * 查询今日到诊数
     *
     * @param reportDate 当前日期
     * @return 今日到诊数
     */
    Integer findTodayVisitsNumber(@Param("reportDate") String reportDate);

    /**
     * 查询本周预约数
     *
     * @param firstDayOfWeekString 当前日期所在周的第一天
     * @param reportDate           当前日期
     * @return 本周预约数
     */
    Integer findThisWeekOrderNumber(@Param("firstDayOfWeekString") String firstDayOfWeekString, @Param("reportDate") String reportDate);

    /**
     * 查询本周到诊数
     *
     * @param firstDayOfWeekString 当前日期所在周的第一天
     * @param reportDate           当前日期
     * @return 本周到诊数
     */
    Integer findThisWeekVisitsNumber(@Param("firstDayOfWeekString") String firstDayOfWeekString, @Param("reportDate") String reportDate);

    /**
     * 查询本月预约数
     *
     * @param firstDay4ThisMonthString 本月第一天
     * @param reportDate               当前日期
     * @return 本月预约数
     */
    Integer findThisMonthOrderNumber(@Param("firstDay4ThisMonthString") String firstDay4ThisMonthString, @Param("reportDate") String reportDate);

    /**
     * 查询本月到诊数
     *
     * @param firstDay4ThisMonthString 本月第一天
     * @param reportDate               当前日期
     * @return 本月到诊数
     */
    Integer findThisMonthVisitsNumber(@Param("firstDay4ThisMonthString") String firstDay4ThisMonthString, @Param("reportDate") String reportDate);
}
