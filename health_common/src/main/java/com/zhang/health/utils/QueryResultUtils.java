package com.zhang.health.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.health.constant.MessageConstant;
import com.zhang.health.entity.PageResult;
import com.zhang.health.entity.Result;
import com.zhang.health.pojo.CheckGroup;

import java.util.List;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/25 20:36
 * 用于在控制层中查询列表的方法上判断有没有查找到对应数据
 */
public class QueryResultUtils {

    /**
     * 判断查询出来的单个对象是否为空
     *
     * @param t   查询出来的单个对象
     * @param <T> 泛型
     * @return Result
     */
    public static <T> Result checkQueryResult(T t) {
        if (t == null) {
            return new Result(false, MessageConstant.NO_DATA);
        }
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, t);
    }


    /**
     * 判断查询出来的集合是否为空
     *
     * @param list 查询出来的集合
     * @param <T>  泛型
     * @return Result
     */
    public static <T> Result checkQueryListResult(List<T> list) {
        if (list != null && list.size() > 0) {
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, list);
        }
        return new Result(false, MessageConstant.NO_DATA);
    }

    /**
     * 判断查询出来的page对象是否为空
     *
     * @param page 查询出来的page对象
     * @param <T>  泛型
     * @return Result
     */
    public static <T> Result checkQueryPageResult(Page<T> page) {
        if (page != null && page.getTotal() > 0) {
            PageResult<T> pageResult =
                    new PageResult<>(page.getTotal(), page.getRecords());
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, pageResult);
        }
        return new Result(false, MessageConstant.NO_DATA);
    }

}
