package com.taotao.rest.test;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * @author:WangYichao
 * @Description:
 * @Date:Created in 2018/4/14 14:29
 */
public class SolrJTest {

    @Test
    public void addData() throws Exception {
        SolrServer solrServer = new HttpSolrServer("http://192.168.28.130:8080/solr4");
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id","item001");
        document.addField("item_title","嘿嘿");
        solrServer.add(document);
        solrServer.commit();
    }
}
