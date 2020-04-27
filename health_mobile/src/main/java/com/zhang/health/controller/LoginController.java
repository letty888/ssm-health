package com.zhang.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.zhang.health.constant.CookieConstant;
import com.zhang.health.constant.MessageConstant;
import com.zhang.health.constant.RedisKeyConstant;
import com.zhang.health.entity.Result;
import com.zhang.health.pojo.Member;
import com.zhang.health.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/27 22:00
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Reference
    private MemberService memberService;

    @PostMapping("/check")
    public Result check(@RequestBody Map<String, String> loginInfo, HttpServletResponse response) {

        if (loginInfo == null || loginInfo.size() <= 0) {
            return new Result(false, MessageConstant.LOGIN_FAIL);
        }
        String telephone = loginInfo.get("telephone");
        String validateCode = loginInfo.get("validateCode");
        //获取redis中存储的验证码
        String redisValidateCode =
                redisTemplate.opsForValue().get(RedisKeyConstant.LOGIN_USER_VALIDATE_CODE + telephone);
        if (telephone == null || validateCode == null || !validateCode.equalsIgnoreCase(redisValidateCode)) {
            return new Result(false, MessageConstant.LOGIN_FAIL);
        }

        //判断该用户是否是会员,如果不是,自动注册为会员
        Member member = memberService.findByTelephone(telephone);
        if (member == null) {
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            memberService.save(member);
        }

        //将用户信息存到cookie中
        Cookie cookie = new Cookie(CookieConstant.LOGIN_USER_PHONE_NUMBER, member.getPhoneNumber());
        //设置cookie的获取范围。默认情况下，设置当前的虚拟目录,如果要共享，则可以将path设置为"/"
        cookie.setPath("/");
        //有效期30天(将Cookie数据写到硬盘的文件中。持久化存储。并指定cookie存活时间，时间到后，cookie文件自动失效)
        cookie.setMaxAge(60 * 60 * 30 * 24);
        response.addCookie(cookie);

        //将用户信息存到redis中
        String memberJson = JSON.toJSON(member).toString();
        redisTemplate.opsForHash().put(RedisKeyConstant.LOGIN_USER_INFO, member.getPhoneNumber(), memberJson);
        redisTemplate.expire(RedisKeyConstant.LOGIN_USER_INFO, 30, TimeUnit.MINUTES);
        return new Result(true, MessageConstant.LOGIN_SUCCESS);
    }
}
