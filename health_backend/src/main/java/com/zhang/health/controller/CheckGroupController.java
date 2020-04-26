package com.zhang.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.health.constant.MessageConstant;
import com.zhang.health.entity.QueryPageBean;
import com.zhang.health.entity.Result;
import com.zhang.health.pojo.CheckGroup;
import com.zhang.health.service.CheckGroupService;
import com.zhang.health.utils.PageUtils;
import com.zhang.health.utils.QueryResultUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * @param checkItemIds 检查项ids
     * @param checkGroup   检查组操作参数
     * @return Result
     */
    @PostMapping("/add")
    public Result add(Integer[] checkItemIds, @RequestBody CheckGroup checkGroup) {
        checkGroupService.add(checkItemIds, checkGroup);
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
        return QueryResultUtils.checkQueryPageResult(checkGroupPage);
    }


    /**
     * 根据id查询对应的检查项,用于编辑时的页面数据回显
     *
     * @param id 检查项id
     * @return Result
     */
    @GetMapping("/findById")
    public Result findById(Integer id) {
        CheckGroup checkGroup = checkGroupService.findById(id);
        return QueryResultUtils.checkQueryResult(checkGroup);
    }


    /**
     * 根据检查组的id查询对应的检查项ids
     *
     * @param id 检查组id
     * @return Result
     */
    @GetMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer id) {
        List<Integer> checkItemIds = checkGroupService.findCheckItemIdsByCheckGroupId(id);
        return QueryResultUtils.checkQueryListResult(checkItemIds);
    }

    /**
     * 编辑检查组
     *
     * @param checkItemIds 检查项id
     * @param checkGroup   检查组操作参数
     * @return Result
     */
    @PostMapping("/edit")
    public Result edit(Integer[] checkItemIds, @RequestBody CheckGroup checkGroup) {
        checkGroupService.edit(checkItemIds, checkGroup);
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }


    /**
     * 添加检查套餐时,查询出所有的检查组
     *
     * @return Result
     */
    @GetMapping("/findAll")
    public Result findAll() {
        List<CheckGroup> checkGroupList = checkGroupService.findAll();
        return QueryResultUtils.checkQueryListResult(checkGroupList);
    }
}
