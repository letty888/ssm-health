package com.zhang.health.service;

import com.zhang.health.pojo.Member;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/27 22:16
 */
public interface MemberService {

    /**
     * 根据用户手机号查找该用户是否注册过
     *
     * @param telephone 用户手机号
     * @return Member
     */
    Member findByTelephone(String telephone);

    /**
     * 新增会员
     *
     * @param member 新增会员参数
     */
    void save(Member member);
}
