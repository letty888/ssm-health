package com.zhang.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.health.entity.QueryPageBean;
import com.zhang.health.mapper.CheckItemMapper;
import com.zhang.health.pojo.CheckItem;
import com.zhang.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/25 9:54
 */
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemMapper checkItemMapper;


    /**
     * 添加检查项
     *
     * @param checkItem 检查项参数
     */
    @Override
    public void add(CheckItem checkItem) {
        checkItem.setDeleted(0);
        checkItemMapper.insert(checkItem);
    }


    /**
     * 条件分页查询
     *
     * @param queryPageBeanCondition 查询参数
     * @return Page<CheckItem>
     */
    @Override
    public Page<CheckItem> findPageByCondition(QueryPageBean queryPageBeanCondition) {
        Integer currentPage = queryPageBeanCondition.getCurrentPage();
        Integer pageSize = queryPageBeanCondition.getPageSize();
        String queryString = queryPageBeanCondition.getQueryString();
        Page<CheckItem> page = new Page<>(currentPage, pageSize);
        QueryWrapper<CheckItem> wrapper = new QueryWrapper<>();
        if (queryString != null) {
            //设置查询条件
            wrapper.like("code", queryString).or().like("name", queryString);
        }

        return checkItemMapper.selectPage(page, wrapper);
    }


    /**
     * 根据id查询对应数据,进行数据回显
     *
     * @param id 检查项id
     * @return CheckItem
     */
    @Override
    public CheckItem findById(Integer id) {
        return checkItemMapper.selectById(id);
    }

    /**
     * 根据id删除
     *
     * @param id 检查项id
     */
    @Override
    public void deleteById(Integer id) {
        checkItemMapper.deleteById(id);
    }


    /**
     * 编辑检查项
     *
     * @param checkItem 检查项参数
     */
    @Override
    public void edit(CheckItem checkItem) {
        checkItem.setDeleted(0);
        checkItemMapper.updateById(checkItem);
    }


    /**
     * 新增检查组时查询所有检查项
     *
     * @return List<CheckItem>
     */
    @Override
    public List<CheckItem> findAll() {
        return checkItemMapper.selectList(null);
    }
}
