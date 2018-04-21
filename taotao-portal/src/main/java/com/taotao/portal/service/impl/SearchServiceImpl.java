package com.taotao.portal.service.impl;

import com.taotao.common.pojo.SearchResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.HttpClientUtil;
import com.taotao.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author:WangYichao
 * @Description:
 * @Date:Created in 2018/4/19 21:04
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Value("${SEARCH_BASE_URL}")
    private String SEARCH_BASE_URL;

    @Override
    public SearchResult getQueryResult(String queryString,int page) {
        Map<String,String> param = new HashMap<String,String>();
        param.put("q",queryString);
        param.put("page",page+"");
        String json = HttpClientUtil.doGet(SEARCH_BASE_URL, param);
        TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, SearchResult.class);
        if(taotaoResult.getStatus()==200){
            SearchResult searchResult = (SearchResult) taotaoResult.getData();
            return searchResult;
        }
        return null;
    }
}
