package com.picc.chexian.core.util;

import java.util.List;

import lombok.Data;

/**
 * 分页
 */
@Data
public class PageInfo<T> {
	//排序字段
	private String orderStr;
    // 每页多少行记录
    private int pageSize = 50;
    // 当前第几页
    private int pageNo = 1;
    // 记录总数
    private int totalRows = -1;
    // 开始记录数
    private int startRow;
    // 结束记录数
    private int endRow;
    // 总页数
    private int totalPages;
    // 记录集合
    private List<T> resultList;

    public PageInfo(int pageNo, int pageSize, List<T> resultList, int totalRows) {
        super();
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.resultList = resultList;
        this.totalRows = totalRows;
    }

    public PageInfo() {
    }

    public PageInfo(int pageNo) {
        int v_endrownum = pageNo * pageSize;
        this.startRow = v_endrownum - pageSize;
    }

    public PageInfo(int pageNo, int pageSize) {
        int v_endrownum = pageNo * pageSize;
        this.startRow = v_endrownum - pageSize;
        if (pageSize != 0)
            this.pageSize = pageSize;
    }
    
    public PageInfo(int startRow, int pageSize, boolean isManage) {
    	if(isManage){
    		this.startRow = startRow;
    		this.pageSize = pageSize;
    	}
    }
}
