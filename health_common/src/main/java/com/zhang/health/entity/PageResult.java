package com.zhang.health.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果封装对象
 *
 * @author zhang
 */
@Data
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 6067735633545706742L;
    /**
     * 总记录数
     */
    private Long total;

    /**
     * 当前页结果
     */
    private List<T> rows;

    public PageResult(Long total, List<T> rows) {
        super();
        this.total = total;
        this.rows = rows;
    }
}
