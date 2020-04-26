package com.zhang.health.jobs;

import com.zhang.health.constant.RedisKeyConstant;
import com.zhang.health.utils.QiNiuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/26 14:30
 * 利用定时器定时删除七牛云空间和redis中存储的无效图片(图片或路径)
 */
public class ClearImgJob {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void clearImg() {

        Set<String> set =
                redisTemplate.opsForSet()
                        .difference(RedisKeyConstant.SAVE_QN_FILESET_BIG,
                                RedisKeyConstant.SAVE_DB_FILESET_SMALL);
        if (set != null && set.size() > 0) {
            for (String filePath : set) {
                //删除七牛云空间中无效的图片
                new QiNiuUtils().deleteFile(filePath);
                //删除redis中无效的图片名称
                redisTemplate.opsForSet().remove(RedisKeyConstant.SAVE_QN_FILESET_BIG, filePath);
            }
        }
    }
}
