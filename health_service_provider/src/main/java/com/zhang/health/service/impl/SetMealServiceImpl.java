package com.zhang.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.health.constant.RedisKeyConstant;
import com.zhang.health.entity.QueryPageBean;
import com.zhang.health.mapper.CheckGroupMapper;
import com.zhang.health.mapper.SetMealMapper;
import com.zhang.health.pojo.CheckGroup;
import com.zhang.health.pojo.SetMeal;
import com.zhang.health.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/26 12:14
 */
@Service(interfaceClass = SetMealService.class)
public class SetMealServiceImpl implements SetMealService {


    @Autowired
    private SetMealMapper setMealMapper;
    @Autowired
    private CheckGroupMapper checkGroupMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 添加检查套餐
     *
     * @param checkGroupIds 检查组ids
     * @param setMeal       检查套餐操作参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Integer[] checkGroupIds, SetMeal setMeal) {
        //保存检查套餐
        setMealMapper.insert(setMeal);
        //保存成功后,将图片的路径添加到redis中
        redisTemplate.opsForSet().add(RedisKeyConstant.SAVE_DB_FILESET_SMALL, setMeal.getImg());
        if (checkGroupIds != null && checkGroupIds.length > 0) {
            //向检查套餐,检查组的中间表中设置对应关系
            for (Integer checkGroupId : checkGroupIds) {
                checkGroupMapper.setSetMealRelation(checkGroupId, setMeal.getId());
            }
        }
    }


    /**
     * 条件分页查询检查套餐
     *
     * @param queryPageBean1 分页参数
     * @return Page<SetMeal>
     */
    @Override
    public Page<SetMeal> findPage(QueryPageBean queryPageBean1) {
        Integer currentPage = queryPageBean1.getCurrentPage();
        Integer pageSize = queryPageBean1.getPageSize();
        String queryString = queryPageBean1.getQueryString();
        Page<SetMeal> page = new Page<>(currentPage, pageSize);
        QueryWrapper<SetMeal> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(queryString)) {
            queryWrapper.like("name", queryString)
                    .or()
                    .like("code", queryString)
                    .or()
                    .like("help_code", queryString);
        }
        Page<SetMeal> setMealPage = setMealMapper.selectPage(page, queryWrapper);
        return setMealPage;
    }


    /**
     * 根据套餐id逻辑删除对应的套餐
     *
     * @param id 套餐id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        setMealMapper.deleteById(id);
        //解除套餐和检查组的关系
        checkGroupMapper.cancelSetMealRelation(id);
    }


    /**
     * 根据检查套餐id查询对应的检查套餐
     *
     * @param id 检查套餐id
     * @return SetMeal
     */
    @Override
    public SetMeal findById(Integer id) {
        return setMealMapper.selectById(id);
    }


    /**
     * 根据检查套餐id查询所关联的检查组的ids
     *
     * @param id 检查套餐id
     * @return List<Integer>
     */
    @Override
    public List<Integer> findCheckGroupIdsBySetMealId(Integer id) {
        return setMealMapper.findCheckGroupIdsBySetMealId(id);
    }


    /**
     * 更新检查套餐
     *
     * @param checkGroupIds 与检查套餐关联的所有检查组的id
     * @param setMeal       检查套餐操作参数
     */
    @Override
    public void edit(Integer[] checkGroupIds, SetMeal setMeal) {
        setMealMapper.updateById(setMeal);
        //删除原有的检查套餐和检查组的对应关系
        checkGroupMapper.cancelSetMealRelation(setMeal.getId());
        if (checkGroupIds != null && checkGroupIds.length > 0) {
            for (Integer checkGroupId : checkGroupIds) {
                //添加新的对应关系
                checkGroupMapper.setSetMealRelation(checkGroupId, setMeal.getId());
            }
        }
    }
}
