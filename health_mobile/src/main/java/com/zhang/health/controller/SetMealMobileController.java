package com.zhang.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zhang.health.entity.Result;
import com.zhang.health.pojo.SetMeal;
import com.zhang.health.service.SetMealService;
import com.zhang.health.utils.QueryResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 套餐管理
 *
 * @author zhang
 */

@RestController
@RequestMapping("/setMeal-mobile")
public class SetMealMobileController {

    @Reference
    private SetMealService setmealService;

    /**
     * 查詢所有体检套餐
     *
     * @return Result
     */
    @GetMapping("/getAllSetMeal")
    public Result getAllSetMeal() {
        List<SetMeal> setMealList = setmealService.findAll();
        return QueryResultUtils.checkQueryListResult(setMealList);
    }

    /**
     * 根据套餐id查询套餐详情
     *
     * @param id 套餐id
     * @return Result
     */
    @GetMapping("/findById")
    public Result findById(Integer id) {
        SetMeal setMeal = setmealService.findById(id);
        return QueryResultUtils.checkQueryResult(setMeal);
    }

}
