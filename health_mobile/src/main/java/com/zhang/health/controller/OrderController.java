package com.zhang.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zhang.health.constant.MessageConstant;
import com.zhang.health.constant.RedisKeyConstant;
import com.zhang.health.entity.Result;
import com.zhang.health.pojo.Order;
import com.zhang.health.queryResult.OrderInfo;
import com.zhang.health.service.OrderService;
import com.zhang.health.utils.QueryResultUtils;
import com.zhang.health.utils.SendMessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/27 16:40
 */
@RestController
@RequestMapping("/order")
public class OrderController {


    @Reference
    private OrderService orderService;

    /**
     * 提交预约请求
     *
     * @param map 预约请求参数
     * @return Result
     */
    @PostMapping("/submit")
    public Result submit(@RequestBody Map<String, String> map) {
        if (map.size() > 0) {
            //获取用户选择的预约日期
            String orderDate = map.get("orderDate");
            Result submitResult = orderService.submit(map);
            //如果预约成功则发送短信提醒用户预约日期
            if (submitResult.isFlag()) {
                SendMessageUtils
                        .sendMessage(SendMessageUtils.NOTICE_TEMPLATE_CODE, map.get("telephone"), SendMessageUtils.DATE_STYLE, orderDate);
                return new Result(true, MessageConstant.ORDER_SUCCESS,submitResult.getData());
            }
        }
        return new Result(false, MessageConstant.ORDER_FAIL);
    }


    /**
     * 根据预约id预约相关信息,用于页面数据展示
     *
     * @param id 预约id
     * @return Result
     */
    @GetMapping("/findById")
    public Result findById(Integer id) {
        OrderInfo orderInfo = orderService.findBy(id);
        return QueryResultUtils.checkQueryResult(orderInfo);
    }
}
