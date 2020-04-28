package com.zhang.health.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zhang.health.pojo.Permission;
import com.zhang.health.pojo.Role;
import com.zhang.health.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/28 8:06
 */

@Component
public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询数据库中是否有对应的用户
        User user = userService.findUserByUserName(username);
        if (user == null) {
            //说明用户不存在
            return null;
        }
        //判断用户页面输入的密码是否和数据库中的密码相同(经测试,框架自己会去数据库中判断用户输入的密码是否正确,如果不正确,则抛异常)

        //动态赋予用户角色和权限
        List<GrantedAuthority> list = new ArrayList<>();

        Set<Role> roleSet = user.getRoles();
        if (roleSet != null && roleSet.size() > 0) {
            for (Role role : roleSet) {
                //为用户赋予角色
                list.add(new SimpleGrantedAuthority(role.getKeyword()));
                Set<Permission> permissionSet = role.getPermissions();
                if (permissionSet != null && permissionSet.size() > 0) {
                    for (Permission permission : permissionSet) {
                        //为用户授权
                        list.add(new SimpleGrantedAuthority(permission.getKeyword()));
                    }
                }
            }
        }

        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), list);
    }
}
