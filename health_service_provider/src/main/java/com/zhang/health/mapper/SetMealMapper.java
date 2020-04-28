package com.zhang.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhang.health.pojo.CheckGroup;
import com.zhang.health.pojo.SetMeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


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


    /**
     * 根据套餐id查询套餐详情(包括检查组和检查项)
     *
     * @param setMealId 套餐id
     * @return SetMeal
     */
    SetMeal findById(@Param("setMealId") Integer setMealId);


    /**
     * 查询所有套餐名称
     *
     * @return List<String>
     */
    List<String> findName();


    /**
     * 根据套餐名称去成功预约的表中查询对应套餐的预约数量
     *
     * @param setMealName 套餐名称
     * @return Integer
     */
    Integer findCountByName(@Param("setMealName") String setMealName);

    /**
     * 查询当前热门套餐
     *
     * @return List<Map<String,Object>>
     */
    List<Map<String,Object>> findHotSetmeal();
}
