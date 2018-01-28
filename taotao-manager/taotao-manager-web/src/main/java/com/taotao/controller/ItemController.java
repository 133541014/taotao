package com.taotao.controller;

import com.taotao.common.pojo.EUDataGridModel;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;
import com.taotao.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/item")
public class ItemController {

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemParamService ItemParamService;

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
     * @param page 页码
     * @param rows 每页条数
     * @Description:查询商品列表
     * @Author:WangYichao
     * @Date:2017/12/30 19:44
     */
    @RequestMapping("/list")
    @ResponseBody
    public EUDataGridModel getItemList(int page, int rows) {

        EUDataGridModel euDataGridModel = itemService.getItemList(page, rows);
        return euDataGridModel;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult addItem(TbItem tbItem, String desc,String itemParams) {

        TaotaoResult result = null;
        try {

            result = itemService.addItem(tbItem, desc,itemParams);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return result;

    }

    @RequestMapping(value="/param/list",method = RequestMethod.GET)
    @ResponseBody
    public EUDataGridModel getParamList(int page,int rows){

        EUDataGridModel euDataGridModel = itemService.getItemParamList(page,rows);

        return euDataGridModel;
    }

    @RequestMapping(value="/loadDesc/{id}")
    @ResponseBody
    public TaotaoResult loadItemDesc(@PathVariable Long id){

        TaotaoResult taotaoResult = itemService.loadItemDesc(id);

        return taotaoResult;

    }

    /**
     * @Description:根据分类id查询规格参数
     * @Author:WangYichao
     * @Date:2018/1/27 21:11
     */
    @RequestMapping("/param/query/itemcatid/{catId}")
    @ResponseBody
    public TaotaoResult getParamByItemCatId(@PathVariable(value = "catId")Long catId){

        TbItemParam tbItemParam = ItemParamService.getItemParamByCatId(catId);
        if(tbItemParam==null){
            return new TaotaoResult();
        }
        return TaotaoResult.ok(tbItemParam);
    }

    @RequestMapping("/param/save/{catId}")
    @ResponseBody
    public TaotaoResult saveItemParam(@PathVariable(value = "catId")Long catId,String paramData){

        TbItemParam tbItemParam = new TbItemParam();
        tbItemParam.setItemCatId(catId);
        tbItemParam.setParamData(paramData);
        ItemParamService.addItemParam(tbItemParam);
        return TaotaoResult.ok();
    }
}
