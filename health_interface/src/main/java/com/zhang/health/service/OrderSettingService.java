package com.zhang.health.service;

import com.zhang.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/26 19:41
 */
public interface OrderSettingService {

    /**
     * 读取上传的excel文件,将对应数据保存到数据库中
     *
     * @param orderSettingList 预约对象集合
     */
    void saveAll(List<OrderSetting> orderSettingList);


    /**
     * 根据月份查询该月中每个日期对应的orderSetting对象
     *
     * @param date 2019-03
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getOrderSettingByMonth(String date);


    /**
     * 根据预约日期修改可预约人数
     *
     * @param orderSetting 预约参数
     */
    void editNumberByDate(OrderSetting orderSetting);
}
