package com.taotao.controller;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author:WangYichao
 * @Description:商品分类
 * @Date:Created in 2017/12/31 20:27
 */
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/list")
    @ResponseBody
    public List<EUTreeNode> getEUTree(@RequestParam(value = "id",defaultValue = "0") long parentId){

        List<EUTreeNode> euTree = itemCatService.getEUTree(parentId);

        return euTree;

    }


}
