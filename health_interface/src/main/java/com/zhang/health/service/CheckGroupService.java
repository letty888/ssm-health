package com.zhang.health.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.health.entity.QueryPageBean;
import com.zhang.health.pojo.CheckGroup;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/25 23:20
 */
public interface CheckGroupService {


    /**
     * 新增检查组
     *
     * @param checkitemIds 检查项ids
     * @param checkGroup   检查组操作参数
     */
    void add(Integer[] checkitemIds, CheckGroup checkGroup);


    /**
     * 根据id删除对应的检查项
     *
     * @param id 检查项id
     */
    void delete(Integer id);


    /**
     * 条件分页查询
     *
     * @param queryPageBean 分页参数
     */
    Page<CheckGroup> findPageByCondition(QueryPageBean queryPageBean);
}
