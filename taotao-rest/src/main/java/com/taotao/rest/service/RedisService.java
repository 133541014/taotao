package com.taotao.rest.service;

import com.taotao.common.pojo.TaotaoResult;

/**
 * @author:WangYichao
 * @Description:redis相关服务
 * @Date:Created in 2018/2/22 17:14
 */
public interface RedisService {

    /**
     * @Description:同步首页内容缓存
     * @Author:WangYichao
     * @Date:2018/2/22 17:16
     */
    TaotaoResult syncContent(long contentCid);

    /**
     * @Description:同步商品分类缓存
     * @Author:WangYichao
     * @Date:2018/2/22 17:28
     */
    TaotaoResult syncItemCat();
}
