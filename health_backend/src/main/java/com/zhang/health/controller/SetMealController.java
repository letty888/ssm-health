package com.zhang.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.health.constant.MessageConstant;
import com.zhang.health.constant.RedisKeyConstant;
import com.zhang.health.entity.QueryPageBean;
import com.zhang.health.entity.Result;
import com.zhang.health.pojo.SetMeal;
import com.zhang.health.service.SetMealService;
import com.zhang.health.utils.PageUtils;
import com.zhang.health.utils.QiNiuUtils;
import com.zhang.health.utils.QueryResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/26 11:28
 */
@RestController
@RequestMapping("/setMeal")
public class SetMealController {

    @Reference
    private SetMealService setMealService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 图片上传
     *
     * @param imgFile 图片对象
     * @return Result
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile) {
        if (imgFile == null) {
            return new Result(false, MessageConstant.PIC_FORMAT_ERROR);
        }
        //获取原始文件名
        String originalFilename = imgFile.getOriginalFilename();
        //获取文件后缀
        assert originalFilename != null;
        int lastIndexOf = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(lastIndexOf - 1);
        //使用UUID随机产生文件名称，防止同名文件覆盖
        String fileName = UUID.randomUUID().toString() + suffix;
        String filePath = new QiNiuUtils().upload(imgFile, fileName);
        //将成功上传到七牛云中的图片路径保存到redis中指定名称的set集合中
        redisTemplate.opsForSet().add(RedisKeyConstant.SAVE_QN_FILESET_BIG, filePath);
        return new Result(true, MessageConstant.UPLOAD_SUCCESS, filePath);
    }

    /**
     * 新增检查套餐
     *
     * @param checkGroupIds 检查组ids
     * @param setMeal       检查套餐操作参数
     * @return Result
     */
    @PostMapping("/add")
    public Result add(Integer[] checkGroupIds, @RequestBody SetMeal setMeal) {
        setMealService.add(checkGroupIds, setMeal);
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 条件分页查询检查套餐
     *
     * @param queryPageBean 分页参数
     * @return Result
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        QueryPageBean queryPageBean1 = PageUtils.checkPage(queryPageBean);
        Page<SetMeal> setMealPage = setMealService.findPage(queryPageBean1);
        return QueryResultUtils.checkQueryPageResult(setMealPage);
    }

    /**
     * 根据套餐id逻辑删除对应的套餐
     *
     * @param id 套餐id
     * @return Result
     */
    @GetMapping("/delete")
    public Result delete(Integer id) {
        setMealService.delete(id);
        return new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);
    }
}
