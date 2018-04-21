package com.taotao.search.dao;

import com.taotao.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;

/**
 * @author:WangYichao
 * @Description:
 * @Date:Created in 2018/4/15 16:51
 */
public interface SearchDao {

    /**
     * 查询搜索结果
     * @param solrQuery solr查询对象
     * @return 封装查询结果
     */
    SearchResult getSearchResult(SolrQuery solrQuery) throws Exception;
}
