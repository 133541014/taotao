package com.taotao.test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class PageHelperTest {

    public void ListTest(){

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");

        TbItemMapper tbItemMapper = applicationContext.getBean(TbItemMapper.class);

        TbItemExample tbItemExample = new TbItemExample();

        PageHelper.startPage(1,10);

        List<TbItem> tbItems = tbItemMapper.selectByExample(tbItemExample);

        for (TbItem tbItem:tbItems) {

            System.out.println(tbItem.getTitle());

        }

        PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(tbItems);

        System.out.println(pageInfo.getTotal());


    }


}
