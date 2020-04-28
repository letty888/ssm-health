package com.zhang.health.service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/28 13:14
 */
public interface ReportService {

    /**
     * 查询过去12个月对应的会员数量
     *
     * @return HashMap<String, Object>
     */
    Map<String, Object> getMemberReport();


    /**
     * 套餐占比
     *
     * @return HashMap<String, Object>
     */
    Map<String, Object> getSetMealReport();


    /**
     * 根据页面需要查询相关运营数据
     *
     * @return Map<String, Object>
     */
    Map<String, Object> getBusinessReportData();
}
