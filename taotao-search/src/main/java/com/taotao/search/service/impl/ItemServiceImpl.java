package com.taotao.search.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemMapper;
import com.taotao.search.service.ItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author:WangYichao
 * @Description:
 * @Date:Created in 2018/4/14 15:38
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private SolrServer solrServer;

    @Override
    public TaotaoResult importItemToIndex() throws Exception{
        List<Map<String, Object>> message = tbItemMapper.getItemSearchMessage();
        SolrInputDocument document = null;
        for(Map<String, Object> map:message){
            document = new SolrInputDocument();
            document.addField("id", String.valueOf(map.get("id")));
            document.addField("item_title", String.valueOf(map.get("title")));
            document.addField("item_sell_point", String.valueOf(map.get("sell_point")));
            document.addField("item_price", String.valueOf(map.get("price")));
            document.addField("item_image", String.valueOf(map.get("image")));
            document.addField("item_category_name", String.valueOf(map.get("category_name")));
            //将文档写入索引库
            solrServer.add(document);

        }
        solrServer.commit();

        return TaotaoResult.ok();
    }
}
