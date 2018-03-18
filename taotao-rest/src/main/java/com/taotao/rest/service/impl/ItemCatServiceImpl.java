package com.taotao.rest.service.impl;

import com.taotao.common.util.JsonUtils;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.service.ItemCatService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:WangYichao
 * @Description:
 * @Date:Created in 2018/2/9 16:46
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    private static final Logger logger = LoggerFactory.getLogger(ItemCatServiceImpl.class);

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_ITEM_CAT_KEY}")
    private String REDIS_ITEM_CAT_KEY;

    /**
     * @Description:获取首页商品分类json数据
     * @Author:WangYichao
     * @Date:2018/2/9 16:46
     */
    @Override
    public Map<String, Object> getItemCatData() {

        Map<String,Object> result = new HashMap<String,Object>();

        //从redis缓存中查询商品分类
        try {
            String catNodesStr = jedisClient.get(REDIS_ITEM_CAT_KEY);
            if(StringUtils.isNotBlank(catNodesStr)){
                List<CatNode> catNodes = JsonUtils.jsonToList(catNodesStr, CatNode.class);
                //组装json数据格式
                result.put("data",catNodes);
                return result;
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }


        List<CatNode> catNodes = this.getCatChildren(0l);

        //放入redis缓存中
        try{
            String catNodesStr = JsonUtils.objectToJson(catNodes);
            jedisClient.set(REDIS_ITEM_CAT_KEY,catNodesStr);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        //组装json数据格式
        result.put("data",catNodes);

        return result;
    }

    private List<CatNode> getCatChildren(Long parentId){

        //查询出所有父节点数据
        TbItemCatExample tbItemExample = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = tbItemExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> tbItemCats = tbItemCatMapper.selectByExample(tbItemExample);

        List resultList = new ArrayList();
        CatNode catNode = null;
        int count = 0;
        for(TbItemCat tbItemCat:tbItemCats){

            if(tbItemCat.getIsParent()){
                catNode = new CatNode();
                catNode.setUrl("/products/"+tbItemCat.getId()+".html");
                catNode.setName("<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
                //查询子节点数据
                catNode.setItem(getCatChildren(tbItemCat.getId()));

                resultList.add(catNode);

                count++;
                if(parentId==0&&count>=14){
                    break;
                }

            }else{
                resultList.add("/products/"+tbItemCat.getId()+".html|"+tbItemCat.getName()+"");
            }

        }

        return resultList;

    }


}
