package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridModel;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.HttpClientUtil;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.service.ContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author:WangYichao
 * @Description:
 * @Date:Created in 2018/2/13 16:17
 */
@Service
public class ContentServiceImpl implements ContentService {

    private static final Logger logger = LoggerFactory.getLogger(ContentServiceImpl.class);

    @Autowired
    private TbContentMapper tbContentMapper;

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Value("${REDIS_SYNC_CONTENT}")
    private String REDIS_SYNC_CONTENT;

    @Override
    public EUDataGridModel getContentList(int page, int rows,Long categoryId) {

        PageHelper.startPage(page, rows);
        List<TbContent> list = null;
        if(categoryId==0){
            list = tbContentMapper.selectByExample(null);
        }else{
            TbContentExample example = new TbContentExample();
            example.createCriteria().andCategoryIdEqualTo(categoryId);
            list = tbContentMapper.selectByExample(example);
        }

        PageInfo<TbContent> pageInfo = new PageInfo(list);

        EUDataGridModel euDataGridModel = new EUDataGridModel();

        euDataGridModel.setTotal(pageInfo.getTotal());

        euDataGridModel.setRows(list);


        return euDataGridModel;
    }

    @Override
    public TaotaoResult addContent(TbContent tbContent) {

        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        tbContentMapper.insert(tbContent);

        try {

            HttpClientUtil.doGet(REST_BASE_URL+REDIS_SYNC_CONTENT+tbContent.getCategoryId());

        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }

        return TaotaoResult.ok();
    }
}
