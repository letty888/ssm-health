package com.zhang.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhang.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;


/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/25 9:51
 */
public interface CheckGroupMapper extends BaseMapper<CheckGroup> {

    /**
     * 向中间表中保存检查组和检查项的关系
     *
     * @param checkGroupId 检查组id
     * @param checkItemId  检查项id
     */
    void setCheckItemsRelation(@Param("checkGroupId") Integer checkGroupId, @Param("checkItemId") Integer checkItemId);


    /**
     * 解除这个检查组与所关联的检查项的关系
     *
     * @param checkGroupId 检查组id
     */
    void cancelCheckItemsRelation(@Param("checkGroupId") Integer checkGroupId);


    /**
     * 向检查套餐,检查组的中间表中设置对应关系
     *
     * @param checkGroupId 检查组id
     * @param setMealId    检查套餐id
     */
    void setSetMealRelation(@Param("checkGroupId") Integer checkGroupId, @Param("setMealId") Integer setMealId);


    /**
     * 解除套餐和检查组的关系
     * 检查套餐id
     *
     * @param setMealId
     */
    void cancelSetMealRelation(@Param("setMealId") Integer setMealId);
}
