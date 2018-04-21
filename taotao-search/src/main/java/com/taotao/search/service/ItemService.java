package com.taotao.search.service;

import com.taotao.common.pojo.TaotaoResult;

/**
 * @author:WangYichao
 * @Description:商品服务
 * @Date:Created in 2018/4/14 15:37
 */
public interface ItemService {

    public TaotaoResult importItemToIndex()throws Exception;
}
