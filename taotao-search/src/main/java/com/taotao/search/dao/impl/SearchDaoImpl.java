package com.taotao.search.dao.impl;

import com.taotao.search.dao.SearchDao;
import com.taotao.common.pojo.Item;
import com.taotao.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author:WangYichao
 * @Description:
 * @Date:Created in 2018/4/15 16:52
 */
@Repository
public class SearchDaoImpl implements SearchDao {

    @Autowired
    private SolrServer solrServer;

    @Override
    public SearchResult getSearchResult(SolrQuery solrQuery) throws Exception {
        SearchResult result = new SearchResult();
        List<Item> items = new ArrayList<Item>();
        QueryResponse response = solrServer.query(solrQuery);
        SolrDocumentList documents = response.getResults();
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        for (SolrDocument document:documents) {
            Item item = new Item();
            item.setId((String) document.get("id"));
            //取高亮显示的结果
            List<String> list = highlighting.get(document.get("id")).get("item_title");
            String title = "";
            if (list != null && list.size()>0) {
                title = list.get(0);
            } else {
                title = (String) document.get("item_title");
            }
            item.setTitle(title);
            item.setImage((String) document.get("item_image"));
            item.setPrice((long) document.get("item_price"));
            item.setSell_point((String) document.get("item_sell_point"));
            item.setCategory_name((String) document.get("item_category_name"));
            //添加的商品列表
            items.add(item);
        }
        result.setItemList(items);
        result.setRecordCount(documents.getNumFound());
        return result;
    }
}
