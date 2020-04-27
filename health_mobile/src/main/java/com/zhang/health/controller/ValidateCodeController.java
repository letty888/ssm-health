package com.zhang.health.controller;

import com.zhang.health.constant.MessageConstant;
import com.zhang.health.constant.RedisKeyConstant;
import com.zhang.health.entity.Result;
import com.zhang.health.utils.SendMessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/27 16:24
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 用户预约时发送验证码
     *
     * @param telephone 用户手机号
     * @return Result
     */
    @PostMapping("/send4Order")
    public Result send4Order(String telephone) {
        //发送验证码
        String param = SendMessageUtils
                .sendMessage(SendMessageUtils.VALIDATE_TEMPLATE_CODE, telephone, SendMessageUtils.CODE_STYLE, null);
        //将用户收到的验证码存入到redis中,存储时间为5分钟
        redisTemplate.opsForValue().
                set(RedisKeyConstant.ORDER_USER_VALIDATE_CODE + telephone, param, 5, TimeUnit.MINUTES);
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    /**
     * 用户登录时发送验证码
     *
     * @param telephone 用户手机号
     * @return Result
     */
    @PostMapping("/send4Login")
    public Result send4Login(String telephone) {
        String param = SendMessageUtils
                .sendMessage(SendMessageUtils.VALIDATE_TEMPLATE_CODE, telephone, SendMessageUtils.CODE_STYLE, null);
        //将验证码存到redis中
        redisTemplate.opsForValue().set(RedisKeyConstant.LOGIN_USER_VALIDATE_CODE + telephone, param, 5, TimeUnit.MINUTES);
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
