package com.taotao.service;

import com.taotao.common.pojo.EUDataGridModel;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

/**
 * @author:WangYichao
 * @Description:
 * @Date:Created in 2018/2/13 16:16
 */
public interface ContentService {

    EUDataGridModel getContentList(int page,int rows,Long categoryId);

    TaotaoResult addContent(TbContent tbContent);
}
