package com.zhang.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhang.health.pojo.Member;
import com.zhang.health.pojo.SetMeal;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/25 9:51
 */
public interface MemberMapper extends BaseMapper<Member> {

    /**
     * 查询今日新增会员数
     *
     * @param reportDate 今日日期 2020-04-28
     * @return Integer 今日注册的会员数量
     */
    Integer findTodayNewMemberCount(@Param("reportDate") String reportDate);

    /**
     * 查询今日所有会员总数
     *
     * @param reportDate 今日日期 2020-04-28
     * @return Integer 截止今日的会员总数量
     */
    Integer findTodayTotalMemberCount(@Param("reportDate") String reportDate);

    /**
     * 查询本周新增会员总数
     *
     * @param firstDayOfWeekString 当前日期所在周的第一天
     * @param reportDate           当前日期
     * @return Integer 本周新增会员总数
     */
    Integer findThisWeekNewMember(@Param("firstDayOfWeekString") String firstDayOfWeekString, @Param("reportDate") String reportDate);

    /**
     * 查询本月新增会员数
     *
     * @param firstDay4ThisMonthString 本月第一天
     * @param reportDate               当前日期
     * @return
     */
    Integer findThisMonthMemberCount(@Param("firstDay4ThisMonthString") String firstDay4ThisMonthString, @Param("reportDate") String reportDate);
}
