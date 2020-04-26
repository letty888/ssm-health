package com.zhang.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.health.entity.QueryPageBean;
import com.zhang.health.mapper.CheckGroupMapper;
import com.zhang.health.mapper.CheckItemMapper;
import com.zhang.health.pojo.CheckGroup;
import com.zhang.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/25 23:22
 */
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupMapper checkGroupMapper;
    @Autowired
    private CheckItemMapper checkItemMapper;

    /**
     * 新增检查组
     *
     * @param checkItemIds 检查项ids
     * @param checkGroup   检查组操作参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Integer[] checkItemIds, CheckGroup checkGroup) {
        //保存检查组
        checkGroupMapper.insert(checkGroup);
        //获取检查组的主键
        Integer checkGroupId = checkGroup.getId();
        if (checkItemIds != null && checkItemIds.length > 0) {
            //向中间表中保存检查组和检查项的关系
            for (Integer checkItemId : checkItemIds) {
                checkGroupMapper.setCheckItemsRelation(checkGroupId, checkItemId);
            }
        }
    }


    /**
     * 根据id删除对应的检查项
     *
     * @param id 检查项id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        checkGroupMapper.deleteById(id);
        //解除这个检查组与所关联的检查项的关系
        checkGroupMapper.cancelCheckItemsRelation(id);
    }


    /**
     * 条件分页查询
     *
     * @param queryPageBean 分页参数
     */
    @Override
    public Page<CheckGroup> findPageByCondition(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        Page<CheckGroup> page = new Page<>(currentPage, pageSize);
        QueryWrapper<CheckGroup> wrapper = new QueryWrapper<>();
        if (queryString != null) {
            //设置查询条件
            wrapper.like("code", queryString)
                    .or()
                    .like("name", queryString)
                    .or()
                    .like("help_code", queryString);
        }
        return checkGroupMapper.selectPage(page, wrapper);
    }


    /**
     * 根据id查询对应的检查项,用于编辑时的页面数据回显
     *
     * @param id 检查组id
     * @return CheckGroup
     */
    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupMapper.selectById(id);
    }


    /**
     * 根据检查组的id查询对应的检查项ids
     *
     * @param id 检查组id
     * @return List<Integer>
     */
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkItemMapper.findCheckItemIdsByCheckGroupId(id);
    }


    /**
     * 编辑检查组
     *
     * @param checkItemIds 检查项id
     * @param checkGroup   检查组操作参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(Integer[] checkItemIds, CheckGroup checkGroup) {
        //1.更新检查组
        checkGroupMapper.updateById(checkGroup);
        //2.根据检查组id删除中间表中检查组和检查项的对应关系
        checkGroupMapper.cancelCheckItemsRelation(checkGroup.getId());
        //3.向中间表中重新存储最新的检查组和检查项的关系
        if (checkItemIds != null && checkItemIds.length > 0) {
            for (Integer checkItemId : checkItemIds) {
                checkGroupMapper.setCheckItemsRelation(checkGroup.getId(), checkItemId);
            }
        }
    }


    /**
     * 添加检查套餐时,查询出所有的检查组
     *
     * @return List<CheckGroup>
     */
    @Override
    public List<CheckGroup> findAll() {
        return checkGroupMapper.selectList(null);
    }
}
