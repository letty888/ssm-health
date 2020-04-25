package com.zhang.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.health.constant.MessageConstant;
import com.zhang.health.entity.PageResult;
import com.zhang.health.entity.QueryPageBean;
import com.zhang.health.entity.Result;
import com.zhang.health.pojo.CheckItem;
import com.zhang.health.service.CheckItemService;
import com.zhang.health.utils.PageUtils;
import com.zhang.health.utils.QueryResultUtils;
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
        if (checkItemPage == null) {
            return new Result(false, MessageConstant.NO_DATA);
        } else {
            PageResult<CheckItem> checkItemPageResult =
                    new PageResult<>(checkItemPage.getTotal(), checkItemPage.getRecords());
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItemPageResult);
        }
    }

    /**
     * 新增检查组时查询所有检查项
     *
     * @return Result
     */
    @GetMapping("/findAll")
    public Result findAll() {
        List<CheckItem> checkItemList = checkItemService.findAll();
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItemList);
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
    public Result delete(Integer id) {
        checkItemService.deleteById(id);
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }
}
