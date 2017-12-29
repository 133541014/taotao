package com.taotao.service;

import com.alibaba.fastjson.JSONObject;
import com.taotao.pojo.TbItem;

public interface ItemService {

    TbItem getItemById(long id);

    JSONObject getItemList(int page,int rows);

}
