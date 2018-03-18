package com.taotao.common.pojo;

/**
 * @author:WangYichao
 * @Description:EasyUI Tree节点
 * @Date:Created in 2017/12/31 19:40
 */
public class EUTreeNode {

    private long id;
    private long parentId;
    private String text;
    private String state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }
}
