package com.taotao.controller;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ContentCatService;
import com.taotao.service.ContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author:WangYichao
 * @Description:
 * @Date:Created in 2018/2/12 16:38
 */
@Controller
@RequestMapping("/content/category")
public class ContentCatController {

    private static final Logger logger = LoggerFactory.getLogger(ContentCatController.class);

    @Autowired
    private ContentCatService contentCatService;

    @Autowired
    private ContentService contentService;

    /**
     * @Description:查询内容分类列表
     * @Author:WangYichao
     * @Date:2018/2/12 17:11
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<EUTreeNode> getContentCatList(@RequestParam(value = "id",required = false,defaultValue = "0")Long parentId){
        List<EUTreeNode> resultList = contentCatService.getContentCatListByParentId(parentId);

        return resultList;
    }

    /**
     * @Description:添加内容分类
     * @Author:WangYichao
     * @Date:2018/2/12 17:11
     */
    @RequestMapping("/create")
    @ResponseBody
    public TaotaoResult addContentCatNode(Long parentId,String name){

        TaotaoResult result = null;
        try{
            result = contentCatService.addContentCatNode(parentId,name);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }

        return result;
    }

    @RequestMapping("/update")
    @ResponseBody
    public TaotaoResult updateContentCatNode(Long id,String name){

        TaotaoResult taotaoResult = contentCatService.updateContentCatNode(id, name);

        return taotaoResult;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult deleteContentCatNode(Long id,Long parentId){
        TaotaoResult result = null;
        try {
            result = contentCatService.deleteContentCatNode(id, parentId);
        } catch (Exception e) {
           logger.error(e.getMessage(),e);
        }
        return result;
    }


}
