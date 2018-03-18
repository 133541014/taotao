package com.taotao.rest.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.RedisService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author:WangYichao
 * @Description:
 * @Date:Created in 2018/2/22 17:16
 */
@Service
public class RedisServiceImpl implements RedisService {
    private static final Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_CONTENT_KEY}")
    private String REDIS_CONTENT_KEY;

    @Value("${REDIS_ITEM_CAT_KEY}")
    private String REDIS_ITEM_CAT_KEY;

    @Override
    public TaotaoResult syncContent(long contentCid) {

        try {
            jedisClient.hdel(REDIS_CONTENT_KEY,contentCid+"");
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return TaotaoResult.build(500, ExceptionUtils.getStackTrace(e));
        }

        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult syncItemCat() {
        try {
            jedisClient.del(REDIS_ITEM_CAT_KEY);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return TaotaoResult.build(500, ExceptionUtils.getStackTrace(e));
        }

        return TaotaoResult.ok();
    }
}
