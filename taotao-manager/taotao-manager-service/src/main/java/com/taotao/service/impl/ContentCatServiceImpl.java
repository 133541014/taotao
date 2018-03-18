package com.taotao.service.impl;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.service.ContentCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author:WangYichao
 * @Description:
 * @Date:Created in 2018/2/12 16:28
 */
@Service
public class ContentCatServiceImpl implements ContentCatService {

    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    @Override
    public List<EUTreeNode> getContentCatListByParentId(Long parentId) {

        TbContentCategoryExample example = new TbContentCategoryExample();

        TbContentCategoryExample.Criteria criteria = example.createCriteria();

        criteria.andParentIdEqualTo(parentId);
        criteria.andStatusEqualTo(1);
        //根据排序号降序排列
        example.setOrderByClause("sort_order desc");

        List<TbContentCategory> tbContentCategories = tbContentCategoryMapper.selectByExample(example);

        List<EUTreeNode> euTreeNodes = new ArrayList<EUTreeNode>();

        EUTreeNode euTreeNode = null;
        for(TbContentCategory tbContentCategory:tbContentCategories){
            euTreeNode = new EUTreeNode();
            euTreeNode.setId(tbContentCategory.getId());
            euTreeNode.setText(tbContentCategory.getName());
            euTreeNode.setState(tbContentCategory.getIsParent()?"closed":"open");
            euTreeNode.setParentId(tbContentCategory.getParentId());
            euTreeNodes.add(euTreeNode);
        }

        return euTreeNodes;
    }

    @Override
    public TaotaoResult addContentCatNode(Long parentId, String name) throws Exception{

        //添加内容分类
        TbContentCategory node = addContentCat(parentId, name);

        //查询父节点isParent属性值,并修改
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(parentId);
        List<TbContentCategory> categories = tbContentCategoryMapper.selectByExample(example);
        TbContentCategory category = categories.get(0);
        if(!category.getIsParent()){
            category.setIsParent(true);
            tbContentCategoryMapper.updateByPrimaryKey(category);
        }
        return TaotaoResult.ok(node);
    }

    /**
     * @Description:添加内容分类
     * @Author:WangYichao
     * @Date:2018/2/12 17:23
     */
    private TbContentCategory addContentCat(Long parentId, String name){
        //创建内容分类节点
        TbContentCategory node = new TbContentCategory();
        node.setParentId(parentId);
        node.setName(name);
        node.setSortOrder(1);
        node.setIsParent(false);
        node.setCreated(new Date());
        node.setUpdated(new Date());
        int maxId = tbContentCategoryMapper.getMaxId();
        Long id = (long)(maxId+1);
        node.setId(id);
        node.setStatus(1);
        tbContentCategoryMapper.insert(node);
        return node;
    }

    @Override
    public TaotaoResult updateContentCatNode(Long id, String name) {
        TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
        tbContentCategory.setName(name);
        tbContentCategory.setUpdated(new Date());
        tbContentCategoryMapper.updateByPrimaryKey(tbContentCategory);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteContentCatNode(Long id, Long parentId) throws Exception{
        TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
        //判断是否是父节点,若为父节点，则删除所有节点
        if(tbContentCategory.getIsParent()){
            TbContentCategory param = new TbContentCategory();
            param.setStatus(2);
            TbContentCategoryExample paramExample = new TbContentCategoryExample();
            paramExample.createCriteria().andParentIdEqualTo(id);
            tbContentCategoryMapper.updateByExampleSelective(param,paramExample);
        }
        tbContentCategory.setStatus(2);
        tbContentCategory.setUpdated(new Date());
        tbContentCategoryMapper.updateByPrimaryKey(tbContentCategory);
        //判断父类节点状态
        TbContentCategoryExample example = new TbContentCategoryExample();

        TbContentCategoryExample.Criteria criteria = example.createCriteria();

        criteria.andParentIdEqualTo(parentId);
        criteria.andStatusEqualTo(1);
        List<TbContentCategory> tbContentCategories = tbContentCategoryMapper.selectByExample(example);
        if (tbContentCategories.size()==0){
            TbContentCategory param = new TbContentCategory();
            param.setIsParent(false);
            TbContentCategoryExample paramExample = new TbContentCategoryExample();
            paramExample.createCriteria().andIdEqualTo(parentId);
            tbContentCategoryMapper.updateByExampleSelective(param,paramExample);
        }

        return TaotaoResult.ok();
    }
}
