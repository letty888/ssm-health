package com.zhang.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.health.constant.MessageConstant;
import com.zhang.health.entity.QueryPageBean;
import com.zhang.health.entity.Result;
import com.zhang.health.pojo.CheckItem;
import com.zhang.health.service.CheckItemService;
import com.zhang.health.utils.PageUtils;
import com.zhang.health.utils.QueryResultUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/25 9:34
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    /**
     * 添加检查项
     *
     * @param checkItem 检查项参数
     */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")
    public Result add(@RequestBody CheckItem checkItem) {
        checkItemService.add(checkItem);
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    /**
     * 条件分页查询
     *
     * @param queryPageBean 查询参数
     * @return Page<CheckItem>
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        //对分页条件做健壮性判断
        QueryPageBean queryPageBeanCondition = PageUtils.checkPage(queryPageBean);
        Page<CheckItem> checkItemPage = checkItemService.findPageByCondition(queryPageBeanCondition);
        return QueryResultUtils.checkQueryPageResult(checkItemPage);
    }

    /**
     * 新增检查组时查询所有检查项
     *
     * @return Result
     */
    @GetMapping("/findAll")
    public Result findAll() {
        List<CheckItem> checkItemList = checkItemService.findAll();
        return QueryResultUtils.checkQueryListResult(checkItemList);
    }

    /**
     * 编辑前的数据回显(根据id查询对应的数据)
     *
     * @param id 检查项id
     * @return Result
     */
    @GetMapping("/findById")
    public Result findById(Integer id) {
        CheckItem checkItem = checkItemService.findById(id);
        return QueryResultUtils.checkQueryResult(checkItem);
    }


    /**
     * 编辑检查项
     *
     * @param checkItem 检查项参数
     * @return Result
     */
    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('CHECKITEM_EDIT')")
    public Result edit(@RequestBody CheckItem checkItem) {
        checkItemService.edit(checkItem);
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }


    /**
     * 根据id删除
     *
     * @param id 检查项id
     * @return Result
     */
    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")//权限校验
    public Result delete(Integer id) {
        checkItemService.deleteById(id);
        int i = 1/0;
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }
}
