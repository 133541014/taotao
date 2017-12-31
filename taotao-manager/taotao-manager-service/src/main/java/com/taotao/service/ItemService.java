package com.taotao.service;


import com.taotao.common.pojo.EUDataGridModel;
import com.taotao.pojo.TbItem;

public interface ItemService {

    TbItem getItemById(long id);

    EUDataGridModel getItemList(int page, int rows);

}
