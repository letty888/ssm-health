package com.zhang.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhang.health.mapper.OrderSettingMapper;
import com.zhang.health.pojo.OrderSetting;
import com.zhang.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/26 19:41
 */
@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingMapper orderSettingMapper;


    /**
     * 读取上传的excel文件,将对应数据保存到数据库中
     *
     * @param orderSettingList 预约对象集合
     */
    @Override
    public void saveAll(List<OrderSetting> orderSettingList) {
        if (orderSettingList != null && orderSettingList.size() > 0) {
            for (OrderSetting orderSetting : orderSettingList) {
                //根据预约日期查询数据库中该日期是否已设置可预约人数
                QueryWrapper<OrderSetting> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("order_date", orderSetting.getOrderDate());
                OrderSetting orderSettingDb = orderSettingMapper.selectOne(queryWrapper);
                if (orderSettingDb == null) {
                    orderSettingMapper.insert(orderSetting);
                } else {
                    orderSettingDb.setNumber(orderSetting.getNumber());
                    orderSettingMapper.updateById(orderSettingDb);
                }
            }
        }
    }


    /**
     * 根据月份查询该月中每个日期对应的orderSetting对象
     *
     * @param date 2019-03
     * @return List<Map < String, Object>>
     */
    @Override
    public List<Map<String, Object>> getOrderSettingByMonth(String date) {
        String[] split = date.split("-");
        if (split[1].length() == 1) {
            date = split[0] + "-0" + split[1];
        }
        QueryWrapper<OrderSetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("order_date", date);
        List<OrderSetting> orderSettingList = orderSettingMapper.selectList(queryWrapper);
        List<Map<String, Object>> list = new ArrayList<>();
        if (orderSettingList != null && orderSettingList.size() > 0) {
            for (OrderSetting orderSetting : orderSettingList) {
                Map<String, Object> orderSettingMap = new HashMap<>(0);
                //获得日期 （几号）
                orderSettingMap.put("date", orderSetting.getOrderDate().getDate());
                //可预约人数
                orderSettingMap.put("number", orderSetting.getNumber());
                //已预约人 数
                orderSettingMap.put("reservations", orderSetting.getReservations());
                list.add(orderSettingMap);
            }
        }
        return list;
    }


    /**
     * 根据预约日期修改可预约人数
     *
     * @param orderSetting 预约参数
     */
    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        //首先根据预约日期查找数据库中是否有对应的设置
        QueryWrapper<OrderSetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_date", orderSetting.getOrderDate());
        OrderSetting orderSettingDb = orderSettingMapper.selectOne(queryWrapper);
        if (orderSettingDb == null) {
            //说明还没有设置过此日期的可预约人数,则进行设置
            orderSettingMapper.insert(orderSetting);
        } else {
            orderSettingDb.setNumber(orderSetting.getNumber());
            orderSettingMapper.updateById(orderSettingDb);
        }
    }
}
