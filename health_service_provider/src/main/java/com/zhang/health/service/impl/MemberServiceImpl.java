package com.zhang.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhang.health.mapper.MemberMapper;
import com.zhang.health.pojo.Member;
import com.zhang.health.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/27 22:16
 */
@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    /**
     * 根据用户手机号查找该用户是否注册过
     *
     * @param telephone 用户手机号
     * @return Member
     */
    @Override
    public Member findByTelephone(String telephone) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone_number", telephone);
        return memberMapper.selectOne(queryWrapper);
    }


    /**
     * 新增会员
     *
     * @param member 新增会员参数
     */
    @Override
    public void save(Member member) {
        memberMapper.insert(member);
    }
}
