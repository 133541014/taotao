package com.taotao.controller;

import com.taotao.common.pojo.EUDataGridModel;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * @Description:测试接口
     * @Author:WangYichao
     * @Date:2017/12/30 20:03
     */
    @RequestMapping("/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable long itemId) {

        TbItem item = itemService.getItemById(itemId);

        return item;
    }

   /**
    * @Description:查询商品列表
    * @param page 页码
    * @param rows 每页条数
    * @Author:WangYichao
    * @Date:2017/12/30 19:44
    */
    @RequestMapping("/list")
    @ResponseBody
    public EUDataGridModel getItemList(int page, int rows){

        EUDataGridModel euDataGridModel = itemService.getItemList(page, rows);
        return euDataGridModel;
    }


}
