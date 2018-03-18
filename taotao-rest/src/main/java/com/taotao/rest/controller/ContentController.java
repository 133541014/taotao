package com.taotao.rest.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.rest.service.ContentService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author:WangYichao
 * @Description:
 * @Date:Created in 2018/2/14 10:26
 */
@Controller
@RequestMapping("/content")
public class ContentController {

    private static final Logger logger = LoggerFactory.getLogger(ContentController.class);

    @Autowired
    private ContentService contentService;

    @RequestMapping("/list/{categoryId}")
    @ResponseBody
    public TaotaoResult getCatgoryContentList(@PathVariable("categoryId") Long categoryId){
        TaotaoResult result = null;
        try {
            result = contentService.getCategoryContentList(categoryId);
        }catch (Exception e){
            result = TaotaoResult.build(500, ExceptionUtils.getStackTrace(e));
        }

        return result;
    }
}
