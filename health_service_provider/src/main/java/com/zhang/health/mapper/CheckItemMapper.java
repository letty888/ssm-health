package com.zhang.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhang.health.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/25 9:51
 */
public interface CheckItemMapper extends BaseMapper<CheckItem> {

    /**
     * 根据检查组的id查询对应的检查项ids
     *
     * @param checkGroupId 检查组id
     * @return List<Integer>
     */
    List<Integer> findCheckItemIdsByCheckGroupId(@Param("checkGroupId") Integer checkGroupId);
}
