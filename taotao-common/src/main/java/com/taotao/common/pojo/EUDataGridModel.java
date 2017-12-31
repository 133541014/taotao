package com.taotao.common.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:WangYichao
 * @Description:datagrid接收json
 * @Date:Created in 2017/12/30 19:57
 */
public class EUDataGridModel {

    private long total;

    private List<?> rows;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
