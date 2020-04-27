package com.zhang.health.constant;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/26 14:00
 */
public class RedisKeyConstant {

    /**
     * 成功保存到数据库中的图片集合
     */
    public final static String SAVE_DB_FILESET_SMALL = "SAVE_DB_FILESET";

    /**
     * 所有上传到七牛云空间中的图片集合
     */
    public final static String SAVE_QN_FILESET_BIG = "SAVE_QN_FILESET";

    /**
     * 预约时用户收到的验证码
     */
    public final static String ORDER_USER_VALIDATE_CODE = "ORDER_USER_VALIDATE_CODE";

    /**
     * 登录时用户收到的验证码
     */
    public final static String LOGIN_USER_VALIDATE_CODE = "LOGIN_USER_VALIDATE_CODE";


    /**
     * 用户登录成功后将该用户信息保存到reids指定的key的hash中
     */
    public final static String LOGIN_USER_INFO = "LOGIN_USER_INFO";
}
