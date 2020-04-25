package com.zhang.health.exceptionHandler;

import com.zhang.health.constant.MessageConstant;
import com.zhang.health.entity.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/25 21:17
 * 公共异常处理类
 */
@ControllerAdvice
@EnableWebMvc
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return new Result(false, MessageConstant.OPERATION_EXCEPTION, e.getMessage());
    }
}
