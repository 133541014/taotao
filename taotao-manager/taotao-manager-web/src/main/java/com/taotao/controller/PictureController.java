package com.taotao.controller;

import com.taotao.common.util.JsonUtils;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author:WangYichao
 * @Description:图片相关action
 * @Date:Created in 2018/1/2 14:00
 */
@Controller
public class PictureController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/pic/upload")
    @ResponseBody
    public Map uploadImage(MultipartFile uploadFile){

        Map map = itemService.uploadImage(uploadFile);

        //chrome接收json字符串会报错
        //String result = JsonUtils.objectToJson(map);

        return map;


    }
}
