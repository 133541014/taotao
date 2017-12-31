package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping(value="/")
    public String toIndex(){
        return "index";
    }

    @RequestMapping(value = "/{page}")
    public String toPage(@PathVariable String page){

        return page;
    }
}
