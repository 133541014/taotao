package com.taotao.service;

import com.taotao.common.pojo.EUTreeNode;

import java.util.List;

/**
 * @author:WangYichao
 * @Description:
 * @Date:Created in 2017/12/31 19:43
 */
public interface ItemCatService {

    List<EUTreeNode> getEUTree(long parentId);
}
