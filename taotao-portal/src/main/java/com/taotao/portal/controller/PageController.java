package com.taotao.portal.controller;

import com.taotao.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author:WangYichao
 * @Description:
 * @Date:Created in 2018/2/4 20:20
 */
@Controller
public class PageController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/index")
    public String toIndex(Model model){

        String advertisement = contentService.getContentAdvertisementList();
        model.addAttribute("ad1",advertisement);
        return "index";
    }
}
