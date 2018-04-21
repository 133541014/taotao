package com.taotao.portal.service;

import com.taotao.common.pojo.SearchResult;

/**
 * @author:WangYichao
 * @Description:
 * @Date:Created in 2018/4/19 21:02
 */
public interface SearchService {

    /**
     * 获取查询结果
     * @param queryString 查询字符串
     * @param page 页码
     * @return 结果封装
     */
    SearchResult getQueryResult(String queryString,int page);
}
