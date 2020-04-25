package com.zhang.health.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 封装返回结果
 *
 * @author zhang
 */
@Data
public class Result implements Serializable {

    private static final long serialVersionUID = 2740901565169486575L;
    /**
     * 执行结果，true为执行成功 false为执行失败
     */
    private boolean flag;

    /**
     * 返回结果信息，主要用于页面提示信息
     */
    private String message;

    /**
     * 返回数据
     */
    private Object data;

    public Result(boolean flag, String message) {
        super();
        this.flag = flag;
        this.message = message;
    }

    public Result(boolean flag, String message, Object data) {
        this.flag = flag;
        this.message = message;
        this.data = data;
    }
}
