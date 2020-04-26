package com.zhang.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhang.health.pojo.CheckGroup;
import com.zhang.health.pojo.SetMeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/25 9:51
 */
public interface SetMealMapper extends BaseMapper<SetMeal> {


    /**
     * 根据检查套餐id查询所关联的检查组的ids
     *
     * @param setMealId 检查套餐id
     * @return List<Integer>
     */
    List<Integer> findCheckGroupIdsBySetMealId(@Param("setMealId") Integer setMealId);
}
