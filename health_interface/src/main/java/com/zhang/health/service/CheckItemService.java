package com.zhang.health.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.health.entity.QueryPageBean;
import com.zhang.health.pojo.CheckItem;

import java.util.List;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/25 9:53
 */
public interface CheckItemService {

    /**
     * 添加检查项
     *
     * @param checkItem 检查项参数
     */
    void add(CheckItem checkItem);


    /**
     * 条件分页查询
     *
     * @param queryPageBean 查询参数
     * @return Page<CheckItem>
     */
    Page<CheckItem> findPageByCondition(QueryPageBean queryPageBean);


    /**
     * 根据id查询对应数据,进行数据回显
     *
     * @param id 检查项id
     * @return CheckItem
     */
    CheckItem findById(Integer id);


    /**
     * 根据id删除
     *
     * @param id 检查项id
     */
    void deleteById(Integer id);


    /**
     * 编辑检查项
     *
     * @param checkItem 检查项参数
     */
    void edit(CheckItem checkItem);


    /**
     * 新增检查组时查询所有检查项
     *
     * @return List<CheckItem>
     */
    List<CheckItem> findAll();
}
