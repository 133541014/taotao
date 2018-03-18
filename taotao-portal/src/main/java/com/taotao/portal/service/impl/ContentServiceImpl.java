package com.taotao.portal.service.impl;

import com.taotao.common.pojo.ContentAdvertisementNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.HttpClientUtil;
import com.taotao.common.util.JsonUtils;
import com.taotao.pojo.TbContent;
import com.taotao.portal.service.ContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:WangYichao
 * @Description:首页分类内容展示service
 * @Date:Created in 2018/2/14 16:24
 */
@Service
public class ContentServiceImpl implements ContentService {

    private static final Logger logger = LoggerFactory.getLogger(ContentServiceImpl.class);

    @Value("${REST_BASE_URL}")
    private String restBaseUtl;

    @Value("${REST_INDEX_AD_URL}")
    private String restIndexADUrl;

    @Override
    public String getContentAdvertisementList() {

        String result = "";
        try {
            String resultStr = HttpClientUtil.doGet(restBaseUtl + restIndexADUrl);
            TaotaoResult taotaoResult = TaotaoResult.formatToList(resultStr, TbContent.class);
            List<TbContent> tbContents = (List<TbContent>) taotaoResult.getData();
            List<ContentAdvertisementNode> nodes = new ArrayList<ContentAdvertisementNode>();
            ContentAdvertisementNode node = null;
            for (TbContent tbContent : tbContents) {
                node = new ContentAdvertisementNode();
                node.setSrc(tbContent.getPic());
                node.setHeight(240);
                node.setWidth(670);
                node.setSrcB(tbContent.getPic2());
                node.setHeightB(240);
                node.setWidthB(550);
                node.setAlt(tbContent.getSubTitle());
                node.setHref(tbContent.getUrl());
                nodes.add(node);
            }
            result = JsonUtils.objectToJson(nodes);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return result;
    }
}
