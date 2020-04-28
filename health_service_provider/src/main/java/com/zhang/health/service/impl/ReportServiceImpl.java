package com.zhang.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhang.health.mapper.MemberMapper;
import com.zhang.health.mapper.OrderMapper;
import com.zhang.health.mapper.SetMealMapper;
import com.zhang.health.pojo.Member;
import com.zhang.health.service.ReportService;
import com.zhang.health.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/28 13:14
 */
@Service
public class ReportServiceImpl implements ReportService {


    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private SetMealMapper setMealMapper;
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 查询过去12个月对应的会员数量
     *
     * @return HashMap<String, Object>
     */
    @Override
    public Map<String, Object> getMemberReport() {
        //获取当前时间
        Calendar calendar = Calendar.getInstance();
        //获取当前时间过去的1个月
        calendar.add(Calendar.MONTH, -12);
        List<String> monthList = new ArrayList<>();
        int index = 12;
        for (int i = 0; i < index; i++) {
            //注意:calendar现在指向的是去年的这个时候,所以没循环一次,就会往后推1个月
            calendar.add(Calendar.MONTH, 1);
            try {
                monthList.add(DateUtils.parseDate2String(calendar.getTime(), "yyyy-MM"));
            } catch (Exception e) {
                throw new RuntimeException("日期格式化出现异常!");
            }
        }
        List<Integer> memberCountList = new ArrayList<>();
        if (monthList.size() > 0) {
            for (String count : monthList) {
                QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
                queryWrapper.like("reg_time", count);
                Integer memberCount = memberMapper.selectCount(queryWrapper);
                memberCountList.add(memberCount);
            }
        }
        //封装返回页面的数据
        HashMap<String, Object> map = new HashMap<>(0);
        map.put("months", monthList);
        map.put("memberCount", memberCountList);
        return map;
    }


    /**
     * 套餐占比
     *
     * @return HashMap<String, Object>
     */
    @Override
    public Map<String, Object> getSetMealReport() {
        //查询所有套餐名称
        List<String> setMealNames = setMealMapper.findName();
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        if (setMealNames != null && setMealNames.size() > 0) {
            for (String setMealName : setMealNames) {
                HashMap<String, Object> dataMap = new HashMap<>(0);
                //根据套餐名称去成功预约的表中查询对应套餐的预约数量
                Integer count = setMealMapper.findCountByName(setMealName);
                dataMap.put("name", setMealName);
                dataMap.put("value", count);
                list.add(dataMap);
            }
        }
        //封装返回结果
        HashMap<String, Object> map = new HashMap<>(0);
        map.put("setmealNames", setMealNames);
        map.put("setmealCount", list);
        return map;
    }


    /**
     * 根据页面需要查询相关运营数据
     *
     * @return Map<String, Object>
     */
    @Override
    public Map<String, Object> getBusinessReportData() {
        return this.getData();

    }

    /*reportDate: null,
      todayNewMember: 0,
      totalMember: 0,
      thisWeekNewMember: 0,
      thisMonthNewMember: 0,
      todayOrderNumber: 0,
      todayVisitsNumber: 0,
      thisWeekOrderNumber: 0,
      thisWeekVisitsNumber: 0,
      thisMonthOrderNumber: 0,
      thisMonthVisitsNumber: 0,
      hotSetmeal: []*/
    private Map<String, Object> getData() {
        Map<String, Object> map = new HashMap<>(0);
        String reportDate = null;
        try {
            reportDate = DateUtils.parseDate2String(DateUtils.getToday(), "yyyy-MM-dd");
            //封装 reportDate:  查询日期
            map.put("reportDate", reportDate);
            //封装todayNewMember: 今日新增会员数
            Integer todayNewMemberCount = memberMapper.findTodayNewMemberCount(reportDate);
            map.put("todayNewMember", todayNewMemberCount);
            //封装totalMember 截止今日总会员数
            Integer todayTotalMemberCount = memberMapper.findTodayTotalMemberCount(reportDate);
            map.put("totalMember", todayTotalMemberCount);
            //封装thisWeekNewMember 本周新增会员数
            //获取当前日期所在周的开始日期
            Date firstDayOfWeek = DateUtils.getFirstDayOfWeek(new Date());
            String firstDayOfWeekString = DateUtils.parseDate2String(firstDayOfWeek, "yyyy-MM-dd");
            Integer thisWeekNewMember = memberMapper.findThisWeekNewMember(firstDayOfWeekString, reportDate);
            map.put("thisWeekNewMember", thisWeekNewMember);
            //封装thisMonthNewMember 本月新增会员数
            Date firstDay4ThisMonth = DateUtils.getFirstDay4ThisMonth();
            String firstDay4ThisMonthString = DateUtils.parseDate2String(firstDay4ThisMonth, "yyyy-MM-dd");
            Integer thisMonthNewMember = memberMapper.findThisMonthMemberCount(firstDay4ThisMonthString, reportDate);
            map.put("thisMonthNewMember", thisMonthNewMember);
            //封装todayOrderNumber 今日预约数
            Integer todayOrderNumber = orderMapper.findTodayOrderNumber(reportDate);
            map.put("todayOrderNumber", todayOrderNumber);
            //封装todayVisitsNumber 今日到诊数
            Integer todayVisitsNumber = orderMapper.findTodayVisitsNumber(reportDate);
            map.put("todayVisitsNumber", todayVisitsNumber);
            //封装thisWeekOrderNumber 本周预约数
            Integer thisWeekOrderNumber = orderMapper.findThisWeekOrderNumber(firstDayOfWeekString, reportDate);
            map.put("thisWeekOrderNumber", thisWeekOrderNumber);
            //封装 thisWeekVisitsNumber 本周到诊数
            Integer thisWeekVisitsNumber = orderMapper.findThisWeekVisitsNumber(firstDayOfWeekString, reportDate);
            map.put("thisWeekVisitsNumber", thisWeekVisitsNumber);
            //封装thisMonthOrderNumber 本月预约数
            Integer thisMonthOrderNumber = orderMapper.findThisMonthOrderNumber(firstDay4ThisMonthString, reportDate);
            map.put("thisMonthOrderNumber", thisMonthOrderNumber);
            //封装 thisMonthVisitsNumber 本月到诊数
            Integer thisMonthVisitsNumber = orderMapper.findThisMonthVisitsNumber(firstDay4ThisMonthString, reportDate);
            map.put("thisMonthVisitsNumber", thisMonthVisitsNumber);
            List<Map<String, Object>> hotSetMeal = setMealMapper.findHotSetmeal();
            map.put("hotSetmeal", hotSetMeal);
        } catch (Exception e) {
            throw new RuntimeException("日期解析出现异常");
        }
        return map;
    }
}
