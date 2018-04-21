package com.taotao.mapper;

import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import com.taotao.vo.ItemVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TbItemMapper {
    int countByExample(TbItemExample example);

    int deleteByExample(TbItemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbItem record);

    int insertSelective(TbItem record);

    List<TbItem> selectByExample(TbItemExample example);

    TbItem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbItem record, @Param("example") TbItemExample example);

    int updateByExample(@Param("record") TbItem record, @Param("example") TbItemExample example);

    int updateByPrimaryKeySelective(TbItem record);

    int updateByPrimaryKey(TbItem record);

    List<ItemVO> getItemList();

    /**
     * 获取商品检索索引库信息
     * @return
     */
    List<Map<String,Object>> getItemSearchMessage();

}