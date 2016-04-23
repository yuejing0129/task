package com.jing.system.dao.sql;

import java.util.ArrayList;
import java.util.List;

/**
 * 拼接sql的工具类
 * @author yuejing
 * @date 2013-11-20 下午11:51:53
 * @version V1.0.0
 */
public abstract class Sql {

	protected String sql;
	//主键列名
	protected String pkKey;
	//主键值
	protected Object pkValue;
	protected String orderby;
	protected List<String> conds;
	protected List<Object> values;

	public Sql() {
	}
	public Sql(String sql) {
		this.sql = sql;
	}
	
	public Sql(String sql, String pkKey, Object pkValue) {
		this.sql = sql;
		this.pkKey = pkKey;
		this.pkValue = pkValue;
	}
	
	public Sql(String sql, String orderby) {
		this.sql = sql;
		this.orderby = orderby;
	}

	/**
	 * 添加参数值
	 * @param cond
	 * @param value
	 */
	public void addValue(Object value) {
		if(values == null) {
			values = new ArrayList<Object>();
		}
		values.add(value);
	}

	/**
	 * 获取拼接的完整的SQL
	 * @return
	 */
	public abstract String getSql();

	/**
	 * 获取参数的值集合
	 * @return
	 */
	public Object[] getParams() {
		return values == null ? null : values.toArray();
	}
	
}