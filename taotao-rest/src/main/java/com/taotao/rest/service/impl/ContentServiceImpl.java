package com.taotao.rest.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.JsonUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ContentService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:WangYichao
 * @Description:
 * @Date:Created in 2018/2/14 10:23
 */
@Service
public class ContentServiceImpl implements ContentService {

    private static final Logger logger = LoggerFactory.getLogger(ContentServiceImpl.class);

    @Autowired
    private TbContentMapper tbContentMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_CONTENT_KEY}")
    private String REDIS_CONTENT_KEY;

    @Override
    public TaotaoResult getCategoryContentList(Long categoryId) {

        //从redis缓存中查询
        try {
            String contentStr = jedisClient.hget(REDIS_CONTENT_KEY, categoryId + "");

            if(StringUtils.isNotBlank(contentStr)){
                //若查询结果不为空
                List<TbContent> tbContents = JsonUtils.jsonToList(contentStr, TbContent.class);

                return TaotaoResult.ok(tbContents);

            }

        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }

        TbContentExample example = new TbContentExample();

        example.createCriteria().andCategoryIdEqualTo(categoryId);

        List<TbContent> tbContents = tbContentMapper.selectByExample(example);

        //存入redis缓存中
        try {
            String listStr = JsonUtils.objectToJson(tbContents);
            jedisClient.hset(REDIS_CONTENT_KEY,categoryId+"",listStr);

        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }

        return TaotaoResult.ok(tbContents);
    }
}
