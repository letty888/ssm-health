package com.zhang.health.utils;

import com.zhang.health.constant.MessageConstant;
import com.zhang.health.entity.Result;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/25 20:36
 * 用于在控制层中查询单个的方法上判断有没有查找到对应数据
 */
public class QueryResultUtils<T> {

    public static <T> Result checkQueryResult(T t) {
        if (t == null) {
            return new Result(false, MessageConstant.NO_DATA);
        }
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, t);
    }

}
