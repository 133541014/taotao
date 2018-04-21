package com.taotao.search.service;

import com.taotao.common.pojo.TaotaoResult;

/**
 * @author:WangYichao
 * @Description:
 * @Date:Created in 2018/4/15 17:01
 */
public interface SearchService {

    /**
     * 获取搜索结果
     * @param queryString 查询结果字符串
     * @param page 页码
     * @param rows 每页显示记录数
     * @return 封装查询结果
     */
    TaotaoResult getSearchResult(String queryString,int page,int rows) throws Exception;
}
