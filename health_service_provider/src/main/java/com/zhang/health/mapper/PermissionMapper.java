package com.zhang.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhang.health.pojo.Permission;
import com.zhang.health.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/28 8:08
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 根据roleId查询出对应的权限信息
     *
     * @param roleId 角色roleId
     * @return Set<Permission>
     */
    Set<Permission> findPermissionSetByRoleId(@Param("roleId") Integer roleId);
}
