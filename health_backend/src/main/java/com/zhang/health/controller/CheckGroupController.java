package com.zhang.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.health.constant.MessageConstant;
import com.zhang.health.entity.PageResult;
import com.zhang.health.entity.QueryPageBean;
import com.zhang.health.entity.Result;
import com.zhang.health.pojo.CheckGroup;
import com.zhang.health.service.CheckGroupService;
import com.zhang.health.utils.PageUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/25 22:44
 */
@RestController
@RequestMapping("/checkGroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;


    /**
     * 新增检查组
     *
     * @param checkitemIds 检查项ids
     * @param checkGroup   检查组操作参数
     * @return Result
     */
    @PostMapping("/add")
    public Result add(Integer[] checkitemIds, @RequestBody CheckGroup checkGroup) {
        checkGroupService.add(checkitemIds, checkGroup);
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    /**
     * 根据id删除对应的检查项
     *
     * @param id 检查项id
     * @return Result
     */
    @GetMapping("/delete")
    public Result delete(Integer id) {
        checkGroupService.delete(id);
        return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }

    /**
     * 条件分页查询
     *
     * @param queryPageBean 分页参数
     * @return Result
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        QueryPageBean queryPageBean1 = PageUtils.checkPage(queryPageBean);
        Page<CheckGroup> checkGroupPage = checkGroupService.findPageByCondition(queryPageBean1);
        if (checkGroupPage == null) {
            return new Result(false, MessageConstant.NO_DATA);
        }else{
            PageResult<CheckGroup> pageResult =
                    new PageResult<>(checkGroupPage.getTotal(), checkGroupPage.getRecords());
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, pageResult);
        }
    }

}
