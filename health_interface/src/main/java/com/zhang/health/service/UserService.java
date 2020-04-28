package com.zhang.health.service;

import com.zhang.health.pojo.User;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/28 8:07
 */
public interface UserService {

    /**
     * 根据用户名查询数据库中是否有对应的用户
     * @param s 用户名
     * @return User
     */
    User findUserByUserName(String s);
}
