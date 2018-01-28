package com.taotao.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridModel;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.FtpUtil;
import com.taotao.common.util.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.*;
import com.taotao.service.ItemService;
import com.taotao.vo.ItemParamVO;
import com.taotao.vo.ItemVO;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:商品相关业务
 * @Author:WangYichao
 * @Date:2018/1/4 20:57
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Autowired
    private TbItemParamMapper tbItemParamMapper;

    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;

    @Value("${ftp.host}")
    private String host;

    @Value("${ftp.port}")
    private Integer port;

    @Value("${ftp.username}")
    private String username;

    @Value("${ftp.password}")
    private String password;

    @Value("${ftp.basepath}")
    private String basePath;

    @Value("${http.image.url}")
    private String imageUrl;


    @Override
    public TbItem getItemById(long id) {

        TbItemExample tbItemExample = new TbItemExample();
        TbItemExample.Criteria criteria = tbItemExample.createCriteria();
        criteria.andIdEqualTo(id);
        List<TbItem> tbItems = tbItemMapper.selectByExample(tbItemExample);
        return tbItems.get(0);
    }

    @Override
    public EUDataGridModel getItemList(int page, int rows) {

        PageHelper.startPage(page, rows);

        List<ItemVO> items = tbItemMapper.getItemList();

        PageInfo<ItemVO> pageInfo = new PageInfo(items);

        EUDataGridModel euDataGridModel = new EUDataGridModel();

        euDataGridModel.setTotal(pageInfo.getTotal());

        euDataGridModel.setRows(items);

        return euDataGridModel;

    }

    /**
     * @Description:商品图片上传
     * @Author:WangYichao
     * @Date:2018/1/2 10:17
     */
    @Override
    public Map uploadImage(MultipartFile multipartFile) {

        Map resultMap = new HashMap();
        try {
            //获取文件名
            String originalFilename = multipartFile.getOriginalFilename();

            //生成随机文件名
            String genImageName = IDUtils.genImageName();

            String fileName = genImageName + originalFilename.substring(originalFilename.lastIndexOf("."));

            String dateUrl = new DateTime().toString("/yyyy/MM/dd/");

            //文件上传
            boolean result = FtpUtil.uploadFile(host, port, username, password, basePath, dateUrl, fileName, multipartFile.getInputStream());

            if (result) {
                resultMap.put("error", 0);
                resultMap.put("url", imageUrl + dateUrl + fileName);
            } else {
                resultMap.put("error", 1);
                resultMap.put("message", "image upload error!");
            }
        } catch (IOException e) {

            resultMap.put("error", 1);
            resultMap.put("message", "image upload error!");
            e.printStackTrace();
        }


        return resultMap;
    }

    /**
     * @Description:添加商品
     * @Author:WangYichao
     * @Date:2018/1/4 20:58
     */
    @Override
    public TaotaoResult addItem(TbItem tbItem, String desc, String itemParams) throws Exception {

        Long id = IDUtils.genItemId();

        tbItem.setId(id);

        tbItem.setStatus((byte) 1);

        tbItem.setCreated(new Date());

        tbItem.setUpdated(new Date());

        tbItemMapper.insert(tbItem);

        //添加商品描述
        addItemDesc(id, desc);
        //添加商品规格参数信息
        addItemParamItem(id, itemParams);

        return TaotaoResult.ok();
    }

    @Override
    public EUDataGridModel getItemParamList(int page, int rows) {

        PageHelper.startPage(page, rows);
        List<ItemParamVO> itemParamList = tbItemParamMapper.getItemParamList();
        PageInfo<ItemParamVO> pageInfo = new PageInfo<>(itemParamList);
        EUDataGridModel euDataGridModel = new EUDataGridModel();
        euDataGridModel.setTotal(pageInfo.getTotal());
        euDataGridModel.setRows(itemParamList);
        return euDataGridModel;
    }

    @Override
    public TaotaoResult loadItemDesc(Long itemId) {

        TbItemDescExample tbItemDescExample = new TbItemDescExample();

        TbItemDescExample.Criteria criteria = tbItemDescExample.createCriteria();

        criteria.andItemIdEqualTo(itemId);

        List<TbItemDesc> itemDescs = tbItemDescMapper.selectByExample(tbItemDescExample);

        return TaotaoResult.ok(itemDescs.get(0));
    }

    /**
     * @Description:添加商品规格参数信息
     * @Author:WangYichao
     * @itemId 商品编号
     * @itemParams 商品规格参数信息
     * @Date:2018/1/28 16:27
     */
    private void addItemParamItem(Long itemId, String itemParams) {
        TbItemParamItem itemParamItem = new TbItemParamItem();
        itemParamItem.setItemId(itemId);
        itemParamItem.setParamData(itemParams);
        itemParamItem.setCreated(new Date());
        itemParamItem.setUpdated(new Date());
        tbItemParamItemMapper.insert(itemParamItem);
    }

    private void addItemDesc(Long itemId, String desc) {
        TbItemDesc tbItemDesc = new TbItemDesc();

        tbItemDesc.setItemId(itemId);

        tbItemDesc.setItemDesc(desc);

        tbItemDesc.setCreated(new Date());

        tbItemDesc.setUpdated(new Date());
        tbItemDescMapper.insert(tbItemDesc);
    }
}
