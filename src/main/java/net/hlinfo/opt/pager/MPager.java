package net.hlinfo.opt.pager;

import java.io.Serializable;

import net.hlinfo.opt.Jackson;


public class MPager implements PageInfo, Serializable {

	private static final long serialVersionUID = 1L;

	/**
     * 改变这个，当每页大小超过 MAX_FETCH_SIZE 时，这个将是默认的 fetchSize
     */
    public static int DEFAULT_PAGE_SIZE = 20;

    /**
     * ResultSet 最大的 fetch size
     */
    public static int MAX_FETCH_SIZE = 200;

    // private static final int FIRST_PAGE_NUMBER = 1;

    private int pageNumber;
    private int pageSize;
    private int pageCount;
    private int recordCount;

    public MPager() {
        this.pageNumber = 1;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    public MPager(int pageNumber) {
        if (pageNumber < 1) {
            pageNumber = 1;
        }
        this.pageNumber = pageNumber;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    public MPager(int pageNumber, int pageSize) {
        if (pageNumber < 1) {
            pageNumber = 1;
        }
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }
    public MPager(int pageNumber, int pageSize,int recordCount) {
    	if (pageNumber < 1) {
    		pageNumber = 1;
    	}
    	if (pageSize < 1) {
    		pageSize = DEFAULT_PAGE_SIZE;
    	}
    	this.pageNumber = pageNumber;
    	this.pageSize = pageSize;
    	this.recordCount = recordCount;
    }

    public MPager resetPageCount() {
        pageCount = -1;
        return this;
    }

    @Override
    public int getPageCount() {
        if (pageCount < 0) {
            pageCount = (int) Math.ceil((double) recordCount / pageSize);
        }
        return pageCount;
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public int getRecordCount() {
        return recordCount;
    }

    @Override
    public MPager setPageNumber(int pn) {
        if (1 > pn) {
            System.out.println("PageNumber shall start at 1, but input is "+pn+", that mean pager is disable");
         }
        pageNumber = pn;
        return this;
    }

    @Override
    public MPager setPageSize(int pageSize) {
        this.pageSize = (pageSize > 0 ? pageSize : DEFAULT_PAGE_SIZE);
        return resetPageCount();
    }

    @Override
    public MPager setRecordCount(int recordCount) {
        this.recordCount = recordCount > 0 ? recordCount : 0;
        this.pageCount = (int) Math.ceil((double) recordCount / pageSize);
        return this;
    }

    @Override
    public int getOffset() {
        return pageSize * (pageNumber - 1);
    }

    @Override
    public String toString() {
        return Jackson.entityToString(this);
    }

    @Override
    public boolean isFirst() {
        return pageNumber == 1;
    }

    @Override
    public boolean isLast() {
        if (pageCount == 0) {
            return true;
        }
        return pageNumber == pageCount;
    }

    @Override
    public boolean hasNext() {
        return !isLast();
    }

    @Override
    public boolean hasPrevious() {
        return !isFirst();
    }
}
