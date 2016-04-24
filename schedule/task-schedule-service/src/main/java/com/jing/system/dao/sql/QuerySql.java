package com.jing.system.dao.sql;

import java.util.ArrayList;

import com.jing.system.utils.DbUtil;
import com.jing.system.utils.FrameStringUtil;

/**
 * 拼接查询的sql的工具类
 * @author yuejing
 * @date 2013-11-20 下午11:51:53
 * @version V1.0.0
 */
public class QuerySql extends Sql {

	public QuerySql(String sql) {
		super(sql);
	}

	public QuerySql(String sql, String orderby) {
		super(sql, orderby);
	}

	/**
	 * 添加条件
	 * @param cond
	 * @param value
	 */
	public void addCond(String cond, Object value) {
		if(value == null || FrameStringUtil.isEmpty(value.toString())) {
			return;
		}
		if(conds == null) {
			conds = new ArrayList<String>();
			values = new ArrayList<Object>();
		}
		conds.add(cond);
		values.add(value);
	}

	/**
	 * 添加模糊查询条件（在字符串前面和后面补全%）
	 * @param cond
	 * @param value
	 */
	public void addCondLike(String cond, Object value) {
		if(DbUtil.isMysql()) {
			cond += " like CONCAT(CONCAT('%', ?), '%')";
		} else if(DbUtil.isOracle()) {
			cond += " like CONCAT(CONCAT('%', ?), '%')";
		}
		addCond(cond, value);
	}

	/**
	 * 添加模糊查询条件（在字符串后面补全%）
	 * @param cond
	 * @param value
	 */
	public void addCondLikeToRight(String cond, Object value) {
		if(DbUtil.isMysql()) {
			cond += " like CONCAT(?, '%')";
		} else if(DbUtil.isOracle()) {
			cond += " like CONCAT(?, '%')";
		}
		addCond(cond, value);
	}

	/**
	 * 添加条件
	 * @param cond
	 * @param value
	 */
	public void addCond(String cond, Object value1, Object value2) {
		if(value1 == null || FrameStringUtil.isEmpty(value1.toString()) || value2 == null || FrameStringUtil.isEmpty(value2.toString())) {
			return;
		}
		if(conds == null) {
			conds = new ArrayList<String>();
			values = new ArrayList<Object>();
		}
		conds.add(cond);
		values.add(value1);
		values.add(value2);
	}

	/**
	 * 设置排序内容
	 * @param orderby
	 */
	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	/**
	 * 获取拼接的完整的SQL
	 * @return
	 */
	public String getSql() {
		StringBuffer sqlStr = new StringBuffer(sql);
		if(conds != null) {
			if(sql.toLowerCase().contains(" where ")) {
				for (int i = 0; i < conds.size(); i ++) {
					sqlStr.append(" AND ").append(conds.get(i));
					if(conds.get(i).indexOf("?") == -1) {
						sqlStr.append("=?");
					}
				}
			} else {
				sqlStr.append(" WHERE");
				for (int i = 0; i < conds.size(); i ++) {
					sqlStr.append(i == 0 ? " " : " AND ").append(conds.get(i));
					if(conds.get(i).indexOf("?") == -1) {
						sqlStr.append("=?");
					}
				}
			}
		}
		if(FrameStringUtil.isNotEmpty(orderby)) {
			sqlStr.append(" ORDER BY ").append(orderby);
		}
		return sqlStr.toString();
	}
}