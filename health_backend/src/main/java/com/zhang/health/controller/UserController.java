package com.zhang.health.controller;

import com.zhang.health.constant.MessageConstant;
import com.zhang.health.entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/28 9:21
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/getUsername")
    public Result getUsername() {
        //使用安全框架认证通过后,框架会把用户的相关信息保存到安全框架提供的上下文中(底层原理是通过一个用户对应一个session来执行的)
        //固定写法
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user);
        if (user == null) {
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
        return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, user.getUsername());
    }
}
