package com.jing.system.model;

import java.io.Serializable;

/**
 * 分页查询条件的实体类
 * @author yuejing
 * @date 2013-8-10 下午5:16:33
 * @version V1.0.0
 */
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = -4495987129630008126L;

	private static final Integer DEF_PAGE = 1;
	private static final Integer DEF_SIZE = 10;
	
	private Integer page;
	private Integer size;
	private Integer rows;
	private Integer firstPage;
	private Object param;

	public Integer getPage() {
		if(page == null) {
			return DEF_PAGE;
		}
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getSize() {
		if(size == null) {
			return DEF_SIZE;
		}
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
		if(rows != null) {
			this.size = rows;
		}
	}
	public Integer getFirstPage() {
		if(firstPage != null) {
			return firstPage;
		}
		else if(firstPage == null && getPage() != null && getSize() != null) {
			return (getPage() - 1) * getSize();
		}
		return firstPage;
	}
	public void setFirstPage(Integer firstPage) {
		if(firstPage != null) {
			this.firstPage = firstPage;
		}
		else if(firstPage == null && getPage() != null && getSize() != null) {
			this.firstPage = (getPage() - 1) * getSize();
		}
	}
	public Object getParam() {
		return param;
	}
	public void setParam(Object param) {
		this.param = param;
	}
}