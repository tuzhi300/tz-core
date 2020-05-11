package net.kuper.tz.core.mybatis;

import com.github.pagehelper.Page;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by shenchunping on 2018/6/22.
 */
public class Pagination<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "页码")
    private int page;
    @ApiModelProperty(value = "每页最多条数")
    private int pageSize;
    @ApiModelProperty(value = "总条数")
    private long count;
    @ApiModelProperty(value = "总页数")
    private int pageCount;
    @ApiModelProperty(value = "当前页面条数")
    private int currentSize;
    @ApiModelProperty(value = "开始行")
    private int startRow;
    @ApiModelProperty(value = "结束行")
    private int endRow;
    @ApiModelProperty(value = "当页数据")
    private List<T> list;

    public Pagination() {
    }

    public Pagination(List<T> list) {
        if (list instanceof Page) {
            Page page = (Page) list;
            this.count = page.getTotal();
            this.page = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.pageCount = page.getPages();
            this.currentSize = page.size();
            if (this.currentSize == 0) {
                this.startRow = 0;
                this.endRow = 0;
            } else {
                this.startRow = page.getStartRow() + 1;
                this.endRow = this.startRow - 1 + this.currentSize;
            }
            this.list = page.getResult();
        } else if (list instanceof Collection) {
            this.page = 1;
            this.pageSize = list.size();
            this.pageCount = this.pageSize > 0 ? 1 : 0;
            this.currentSize = list.size();
            this.startRow = 0;
            this.endRow = list.size() > 0 ? list.size() - 1 : 0;
            this.list = list;
        }
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
