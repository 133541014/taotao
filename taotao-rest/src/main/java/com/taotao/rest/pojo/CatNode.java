package com.taotao.rest.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * 首页商品分类json节点
 * @author:WangYichao
 * @Description:
 * @Date:Created in 2018/2/9 16:37
 */
public class CatNode {

    @JsonProperty("u")
    private String url;

    @JsonProperty("n")
    private String name;

    @JsonProperty("i")
    private List<?> item;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<?> getItem() {
        return item;
    }

    public void setItem(List<?> item) {
        this.item = item;
    }
}
