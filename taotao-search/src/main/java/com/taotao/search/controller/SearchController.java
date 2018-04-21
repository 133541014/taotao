package com.taotao.search.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author:WangYichao
 * @Description:
 * @Date:Created in 2018/4/15 17:08
 */
@Controller
public class SearchController {
    /**
     * 日志
     */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 搜索服务
     */
    @Autowired
    private SearchService searchService;

    @RequestMapping("/query")
    @ResponseBody
    public TaotaoResult getSearchResult(
            @RequestParam(value = "q",required = false)String queryString,
            @RequestParam(value = "page",defaultValue = "1")int page,
            @RequestParam(value = "rows",defaultValue = "60")int rows){
        if(StringUtils.isBlank(queryString)){
            return TaotaoResult.build(400,"查询条件不能为空");
        }

        TaotaoResult result = null;
        try {
            queryString = new String(queryString.getBytes("iso-8859-1"),"utf-8");
            result = searchService.getSearchResult(queryString,page,rows);
        }catch (Exception e){
            log.error("solr检索出错",e);
            return TaotaoResult.build(500, ExceptionUtils.getStackTrace(e));
        }

        return result;

    }
}
