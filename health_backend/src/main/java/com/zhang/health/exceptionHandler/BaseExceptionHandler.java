package com.zhang.health.exceptionHandler;

import com.zhang.health.constant.MessageConstant;
import com.zhang.health.entity.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
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


    /**
     * 操作方面的异常捕获
     *
     * @param e Exception
     * @return Result
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result operateError(Exception e) {
        e.printStackTrace();
        //输入的用户名或密码错误
        if (e instanceof InternalAuthenticationServiceException || e instanceof BadCredentialsException) {
            return new Result(false, MessageConstant.LOGIN_EXCEPTION, e.getMessage());
        }
        //没有权限进行操作
        if (e instanceof AccessDeniedException) {
            return new Result(false, MessageConstant.HAS_NO_PERMISSION, e.getMessage());
        }
        return new Result(false, MessageConstant.OPERATION_EXCEPTION, e.getMessage());
    }

}
