package com.taotao.controller;

import com.taotao.common.pojo.EUDataGridModel;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author:WangYichao
 * @Description:
 * @Date:Created in 2018/2/13 16:37
 */
@Controller
@RequestMapping("/content")
public class ContentController {

    private static final Logger logger = LoggerFactory.getLogger(ContentController.class);

    @Autowired
    private ContentService contentService;

    @RequestMapping("/query/list")
    @ResponseBody
    public EUDataGridModel getContentList(int page, int rows, Long categoryId){
        EUDataGridModel result = contentService.getContentList(page, rows, categoryId);
        return result;
    }

    @RequestMapping("/save")
    @ResponseBody
    public TaotaoResult addContent(TbContent tbContent){

        TaotaoResult result = contentService.addContent(tbContent);
        return result;
    }
}
