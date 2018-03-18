package com.taotao.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.util.JsonUtils;
import com.taotao.rest.service.ItemCatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author:WangYichao
 * @Description:
 * @Date:Created in 2018/2/9 18:09
 */
@Controller
//@RequestMapping("/rest")
public class ItemCatController {

    private static final Logger logger = LoggerFactory.getLogger(ItemCatController.class);

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping(value = "/itemcat/list",produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
    @ResponseBody
    public String getItemCatList(String callback){

        ObjectMapper objectMapper = new ObjectMapper();
        String resultStr = "";
        try {
            Map<String, Object> itemCatData = itemCatService.getItemCatData();
            resultStr = callback+"("+objectMapper.writeValueAsString(itemCatData)+");";
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(),e);
        }

        return resultStr;
    }


}
