package com.zhang.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhang.health.pojo.Role;
import com.zhang.health.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/28 8:08
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户id查询出所有对应的角色信息
     *
     * @param userId 用户id
     * @return Set<Role>
     */
    Set<Role> findRoleSetByUserId(@Param("userId") Integer userId);
}
