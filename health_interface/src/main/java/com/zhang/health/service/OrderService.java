package com.zhang.health.service;

import com.zhang.health.entity.Result;
import com.zhang.health.queryResult.OrderInfo;

import java.util.Map;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/27 16:57
 */
public interface OrderService {

    /**
     * 提交预约请求
     *
     * @param map 预约请求参数
     * @return Result
     */
    public Result submit(Map<String, String> map);

    /**
     * 根据预约id查询预约相关信息,用于页面数据展示
     *
     * @param id 预约id
     * @return OrderInfo
     */
    OrderInfo findBy(Integer id);
}
