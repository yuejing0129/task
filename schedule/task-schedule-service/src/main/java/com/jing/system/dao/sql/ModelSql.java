package com.jing.system.dao.sql;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.jing.system.dao.annotation.Column;
import com.jing.system.dao.annotation.Table;
import com.jing.system.utils.FrameStringUtil;

/**
 * 拼接查询的sql的工具类
 * @author yuejing
 * @date 2013-11-20 下午11:51:53
 * @version V1.0.0
 */
public class ModelSql extends Sql {

	private Class<?> cls;
	private Map<String, String> columns = new HashMap<String, String>();

	public ModelSql(Class<?> cls) {
		this.cls = cls;
		//获取所有属性
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			//设置些属性是可以访问的
			field.setAccessible(true);
			Column column = field.getAnnotation(Column.class);
			if(column == null) {
				continue;
			}
			columns.put(field.getName(), column.name());
		}
	}

	/**
	 * 获取表名
	 * @return
	 */
	public String getTable() {
		Table table = cls.getAnnotation(Table.class);
		return table.name();
	}
	
	/**
	 * 根据类属性获取数据库列名
	 * @param field
	 * @return
	 */
	public String getColumn(String field) {
		return columns.get(field);
	}

	/**
	 * 获取该实体的所有列名
	 * @return
	 */
	public String getColumns() {
		return getColumns(null);
	}
	/**
	 * 获取列名
	 * @param prefix 前缀，比如a.name  a为传入的前缀
	 * @return
	 */
	public String getColumns(String prefix) {
		if(FrameStringUtil.isNotEmpty(prefix)) {
			prefix += ".";
		} else {
			prefix = "";
		}
		StringBuffer sqlStr = new StringBuffer();
		Iterator<Entry<String, String>> entryKeyIterator = columns.entrySet().iterator();
		while (entryKeyIterator.hasNext()) {
			Entry<String, String> e = entryKeyIterator.next();
			sqlStr.append(prefix).append(e.getValue()).append(" as ").append(e.getKey()).append(",");
		}
		sqlStr.setCharAt(sqlStr.length() - 1, ' ');
		return sqlStr.toString();
	}
	
	/**
	 * 获取查询表所有记录的SQL
	 * @return
	 */
	public String getQueryAllSql() {
		StringBuffer sqlStr = new StringBuffer("select ");
		sqlStr.append(getColumns()).append(" from ").append(getTable());
		return sqlStr.toString();
	}

	@Override
	public String getSql() {
		return null;
	}
}