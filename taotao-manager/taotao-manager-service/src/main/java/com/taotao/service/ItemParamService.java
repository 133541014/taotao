package com.taotao.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;

/**
 * @author:WangYichao
 * @Description:
 * @Date:Created in 2018/1/27 20:29
 */
public interface ItemParamService {

    TbItemParam getItemParamByCatId(Long catId);

    TaotaoResult addItemParam(TbItemParam tbItemParam);

}
