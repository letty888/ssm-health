package com.zhang.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhang.health.constant.MessageConstant;
import com.zhang.health.constant.RedisKeyConstant;
import com.zhang.health.entity.Result;
import com.zhang.health.mapper.MemberMapper;
import com.zhang.health.mapper.OrderMapper;
import com.zhang.health.mapper.OrderSettingMapper;
import com.zhang.health.pojo.Member;
import com.zhang.health.pojo.Order;
import com.zhang.health.pojo.OrderSetting;
import com.zhang.health.queryResult.OrderInfo;
import com.zhang.health.service.OrderService;
import com.zhang.health.service.OrderSettingService;
import com.zhang.health.utils.BeanMapUtils;
import com.zhang.health.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/27 16:57
 */
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private OrderSettingMapper orderSettingMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 提交预约请求
     *
     * @param map 预约请求参数
     * @return Result
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result submit(Map<String, String> map) {
        //获取用户在页面填写的数据
        String telephone = map.get("telephone");
        String validateCode = map.get("validateCode");
        String orderDate = map.get("orderDate");
        String param = redisTemplate.opsForValue().get(RedisKeyConstant.ORDER_USER_VALIDATE_CODE + telephone);
        if (telephone == null || validateCode == null || orderDate == null || !validateCode.equalsIgnoreCase(param)) {
            return new Result(false, MessageConstant.ORDER_FAIL);
        }
        //1.判断用户预约的日期是否已经设置可预约
        QueryWrapper<OrderSetting> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("order_date", orderDate);
        OrderSetting orderSetting = orderSettingMapper.selectOne(queryWrapper1);
        //若该日期没有设置可预约,则返回false
        if (orderSetting == null) {
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //2.判断该日期中是否预约已满
        if (orderSetting.getNumber() <= orderSetting.getReservations()) {
            return new Result(false, MessageConstant.ORDER_FULL);
        }
        //3.用手机号判断该用户是否是会员(去会员表中查找)
        QueryWrapper<Member> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("phone_number", telephone);
        Member member = memberMapper.selectOne(queryWrapper2);
        //如果不是会员,则将该用户添加进会员列表(即自动注册为会员)
        if (member == null) {
            member = new Member();
            member.setIdCard(map.get("idCard"));
            member.setRegTime(new Date());
            member.setPhoneNumber(telephone);
            member.setSex(map.get("sex"));
            member.setName(map.get("name"));
            memberMapper.insert(member);
        } else {
            //如果该用户本来就是会员,判断该会员是否在同一天内预约了同一个体检套餐(在预约成功的表中查找)
            QueryWrapper<Order> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("member_id", member.getId())
                    .eq("order_date", orderDate)
                    .eq("setmeal_id", map.get("setmealId"));
            List<Order> orderList = orderMapper.selectList(queryWrapper3);
            if (orderList != null && orderList.size() > 0) {
                return new Result(false, MessageConstant.HAS_ORDERED);
            }
        }
        //更新ordersetting表中已经预约的人数
        orderSetting.setReservations(orderSetting.getReservations() + 1);
        orderSettingMapper.updateById(orderSetting);
        //向order表中添加该条成功预约的记录
        Order order = null;
        try {
            HashMap<String, Object> saveMap = new HashMap<>(0);
            saveMap.put("orderType", Order.ORDER_TYPE_WEI_XIN);
            saveMap.put("memberId", member.getId());
            Date date = DateUtils.parseString2Date(orderDate);
            saveMap.put("orderDate", date);
            saveMap.put("setmealId", Integer.parseInt(map.get("setmealId")));
            saveMap.put("orderStatus", Order.ORDER_STATUS_NO);
            order =  BeanMapUtils.mapToBean(saveMap, Order.class);
            orderMapper.insert(order);
        } catch (Exception e) {
            throw new RuntimeException(MessageConstant.OPERATION_EXCEPTION);
        }
        return new Result(true, MessageConstant.ORDER_SUCCESS,order.getId());
    }


    /**
     * 根据预约id查询预约相关信息,用于页面数据展示
     *
     * @param id 预约id
     * @return OrderInfo
     */
    @Override
    public OrderInfo findBy(Integer id) {
        return orderMapper.selectOrderInfoById(id);
    }
}
