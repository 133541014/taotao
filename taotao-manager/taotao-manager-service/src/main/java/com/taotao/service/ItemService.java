package com.taotao.service;


import com.taotao.common.pojo.EUDataGridModel;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ItemService {

    TbItem getItemById(long id);

    EUDataGridModel getItemList(int page, int rows);

    Map uploadImage(MultipartFile multipartFile);

    /**
     * @Description:进行两次数据库操作，为保证一致性，出错时显式抛出异常，Spring进行事务回滚
     * @Author:WangYichao
     * @Date:2018/1/6 16:24
     */
    TaotaoResult addItem(TbItem tbItem, String desc) throws Exception;

   /**
    * @Description:查询商品规格参数列表
    * @Author:WangYichao
    * @Date:2018/1/7 11:57
    */
    EUDataGridModel getItemParamList(int page, int rows);

    /**
     * @Description:修改商品信息时加载商品描述
     * @Author:WangYichao
     * @Date:2018/1/7 16:37
     */
    TaotaoResult loadItemDesc(Long itemId);
}
