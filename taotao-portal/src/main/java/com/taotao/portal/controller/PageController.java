package com.taotao.portal.controller;

import com.taotao.common.pojo.SearchResult;
import com.taotao.portal.service.ContentService;
import com.taotao.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author:WangYichao
 * @Description:
 * @Date:Created in 2018/2/4 20:20
 */
@Controller
public class PageController {

    /**
     * 内容服务
     */
    @Autowired
    private ContentService contentService;

    /**
     * 搜索服务
     */
    @Autowired
    private SearchService searchService;

    @RequestMapping("/index")
    public String toIndex(Model model){

        String advertisement = contentService.getContentAdvertisementList();
        model.addAttribute("ad1",advertisement);
        return "index";
    }

    @RequestMapping("/search")
    public String toSearchPage(@RequestParam("q") String queryString,Model model,
                               @RequestParam(value = "page",defaultValue = "1")Integer page) throws Exception {
        if (queryString != null) {
            queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
        }
        SearchResult result = searchService.getQueryResult(queryString,page);
        model.addAttribute("query",queryString);
        model.addAttribute("totalPages",result.getPageCount());
        model.addAttribute("itemList",result.getItemList());
        model.addAttribute("page",page);
        return "search";
    }
}
