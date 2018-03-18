package com.taotao.service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;

import java.util.List;

/**
 * @author:WangYichao
 * @Description:
 * @Date:Created in 2018/2/12 16:27
 */
public interface ContentCatService {

    /**
     * @Description:根据parentId获取内容分类列表
     * @Author:WangYichao
     * @Date:2018/2/12 16:27
     */
    List<EUTreeNode> getContentCatListByParentId(Long parentId);

    /**
     * @Description:添加内容分类
     * @Author:WangYichao
     * @Date:2018/2/12 17:15
     */
    TaotaoResult addContentCatNode(Long parentId,String name) throws Exception;

    /**
     * @Description:修改内容分类节点
     * @Author:WangYichao
     * @Date:2018/2/12 18:08
     */
    TaotaoResult updateContentCatNode(Long id,String name);

    /**
     * @Description:删除内容分类节点
     * @Author:WangYichao
     * @Date:2018/2/12 18:08
     */
    TaotaoResult deleteContentCatNode(Long id,Long parentId) throws Exception;
}
