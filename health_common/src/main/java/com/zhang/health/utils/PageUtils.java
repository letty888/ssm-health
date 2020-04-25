package com.zhang.health.utils;

import com.zhang.health.entity.QueryPageBean;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/25 18:29
 * 分页参数校验工具类
 */
@Data
public class PageUtils {

    public static QueryPageBean checkPage(QueryPageBean queryPageBean) {
        QueryPageBean queryPageBeanCondition = new QueryPageBean();
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        if (currentPage == null || currentPage < 0) {
            currentPage = 0;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 10;
        }
        if (!StringUtils.isEmpty(queryString)) {
            queryString = queryString.trim();
        }

        pageSize = pageSize > 30 ? 30 : pageSize;
        queryPageBeanCondition.setCurrentPage(currentPage);
        queryPageBeanCondition.setPageSize(pageSize);
        queryPageBeanCondition.setQueryString(queryString);
        return queryPageBeanCondition;
    }
}
