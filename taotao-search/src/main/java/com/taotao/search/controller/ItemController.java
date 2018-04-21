package com.taotao.search.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.service.ItemService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author:WangYichao
 * @Description:
 * @Date:Created in 2018/4/14 15:44
 */
@Controller
@RequestMapping("/manager")
public class ItemController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ItemService itemService;

    @RequestMapping("/importIndex")
    @ResponseBody
    public TaotaoResult ImportIndex(){
        TaotaoResult result = null;
        try {
            result = itemService.importItemToIndex();
        }catch (Exception e){
            log.error("导入solr索引出错",e);
            return TaotaoResult.build(500, ExceptionUtils.getStackTrace(e));
        }

        return result;
    }
}
