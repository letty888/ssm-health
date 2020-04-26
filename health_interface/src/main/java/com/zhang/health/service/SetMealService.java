package com.zhang.health.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.health.entity.QueryPageBean;
import com.zhang.health.pojo.SetMeal;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/26 12:13
 */
public interface SetMealService {

    /**
     * 添加检查套餐
     *
     * @param checkGroupIds 检查组ids
     * @param setMeal       检查套餐操作参数
     */
    void add(Integer[] checkGroupIds, SetMeal setMeal);


    /**
     * 条件分页查询检查套餐
     *
     * @param queryPageBean1 分页参数
     * @return Page<SetMeal>
     */
    Page<SetMeal> findPage(QueryPageBean queryPageBean1);


    /**
     * 根据套餐id逻辑删除对应的套餐
     *
     * @param id 套餐id
     */
    void delete(Integer id);
}
