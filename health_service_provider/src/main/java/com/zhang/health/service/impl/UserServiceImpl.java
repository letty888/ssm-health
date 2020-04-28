package com.zhang.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhang.health.mapper.PermissionMapper;
import com.zhang.health.mapper.RoleMapper;
import com.zhang.health.mapper.UserMapper;
import com.zhang.health.pojo.Permission;
import com.zhang.health.pojo.Role;
import com.zhang.health.pojo.User;
import com.zhang.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/28 8:07
 */
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 根据用户名查询数据库中是否有对应的用户
     *
     * @param s 用户名
     * @return User
     */
    @Override
    public User findUserByUserName(String s) {
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        //查询用户的基本信息
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", s);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            //根据用户id查询出所有对应的角色信息
            Set<Role> roleSet = roleMapper.findRoleSetByUserId(user.getId());
            if (roleSet != null && roleSet.size() > 0) {
                for (Role role : roleSet) {
                    //根据roleId查询出对应的权限信息
                    Set<Permission> permissionSet = permissionMapper.findPermissionSetByRoleId(role.getId());
                    if (permissionSet != null && permissionSet.size() > 0) {
                        role.setPermissions(permissionSet);
                    }
                }
            }
            user.setRoles(roleSet);
        }
        return user;
    }
}
