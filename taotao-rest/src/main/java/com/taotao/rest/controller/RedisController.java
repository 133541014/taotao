package com.taotao.rest.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.rest.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author:WangYichao
 * @Description:发布redis相关服务
 * @Date:Created in 2018/2/22 17:23
 */
@Controller
@RequestMapping("/redis/sync")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @RequestMapping("/content/{contentCid}")
    @ResponseBody
    public TaotaoResult redisSyncContent(@PathVariable long contentCid){
        TaotaoResult result = redisService.syncContent(contentCid);
        return  result;
    }

    @RequestMapping("/item/cat")
    @ResponseBody
    public TaotaoResult redisSyncItemCat(){
        TaotaoResult result = redisService.syncItemCat();
        return result;
    }


}
