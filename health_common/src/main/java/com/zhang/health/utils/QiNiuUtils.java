package com.zhang.health.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/26 10:31
 * 七牛云文件上传删除工具类
 */
public class QiNiuUtils {

    private final String bucket = "health-ssm-zhang";
    private UploadManager uploadManager;
    private String upToken;
    private BucketManager bucketManager;
    /**
     * 外域连接前缀
     */
    private static final String prix = "http://q9dhk9z7j.bkt.clouddn.com/";

    public QiNiuUtils() {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        uploadManager = new UploadManager(cfg);
        String accessKey = "Rc_feElMSmVM3iAVXgj9L3RZkYomB0Xe11D6QTMN";
        String secretKey = "ixRQ5YAqKn0eiPbTDflbnZ9MTdlRxgxhqbDqhOTv";
        Auth auth = Auth.create(accessKey, secretKey);
        upToken = auth.uploadToken(bucket);
        bucketManager = new BucketManager(auth, cfg);
    }

    /**
     * 字节流方式上传文件
     *
     * @param file     文件对象
     * @param fileName 自定义的文件名称
     */
    public String upload(MultipartFile file, String fileName) {
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        byte[] uploadBytes;
        try {
            uploadBytes = file.getBytes();
            Response response = uploadManager.put(uploadBytes, fileName, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            //返回请求地址
            return prix + putRet.key + "?t=" + System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据文件名称删除空间中的文件
     *
     * @param fileName 文件名称
     */
    public void deleteFile(String fileName) {
        try {
            bucketManager.delete(bucket, fileName);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }
}
