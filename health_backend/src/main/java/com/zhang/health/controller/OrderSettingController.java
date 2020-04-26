package com.zhang.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zhang.health.constant.MessageConstant;
import com.zhang.health.entity.Result;
import com.zhang.health.pojo.OrderSetting;
import com.zhang.health.service.OrderSettingService;
import com.zhang.health.utils.ExcelImportUtil;
import com.zhang.health.utils.QueryResultUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/26 19:40
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    /**
     * 读取上传的excel文件,将对应数据保存到数据库中
     *
     * @param excelFile excel文件
     * @return Result
     * @throws Exception 异常
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile) throws Exception {
        if (excelFile != null) {
            InputStream is = excelFile.getInputStream();
            List<OrderSetting> orderSettingList =
                    new ExcelImportUtil<OrderSetting>(OrderSetting.class).readExcel(is, 1, 0);
            orderSettingService.saveAll(orderSettingList);
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        }
        return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
    }

    /**
     * 根据月份查询该月中每个日期对应的orderSetting对象
     *
     * @param date 2019-03
     * @return Result
     */
    @PostMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date) {
        List<Map<String, Integer>> list = orderSettingService.getOrderSettingByMonth(date);
        return QueryResultUtils.checkQueryListResult(list);
    }
}
