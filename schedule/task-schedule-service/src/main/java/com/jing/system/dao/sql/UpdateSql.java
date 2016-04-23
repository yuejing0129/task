package com.jing.system.dao.sql;

import java.lang.reflect.Field;

import com.jing.system.dao.annotation.Column;
import com.jing.system.dao.annotation.Table;

/**
 * 拼接修改的sql的工具类
 * @author yuejing
 * @date 2013-11-20 下午11:51:53
 * @version V1.0.0
 */
public class UpdateSql extends Sql {

	private Class<?> cls;
	private Object object;

	public UpdateSql(Object object, String pkKey, Object pkValue) {
		super("update ", pkKey, pkValue);
		this.object = object;
		this.cls = object.getClass();
	}

	/**
	 * 获取拼接的完整的SQL
	 * @return
	 */
	public String getSql() {
		StringBuffer sqlStr = new StringBuffer(sql);
		//获取表名
		Table table = cls.getAnnotation(Table.class);
		sqlStr.append(table.name()).append(" set ");
		//得到类中的所有属性集合
		Field[] fields = cls.getDeclaredFields();
		String pkColumn = null;
		for (Field field : fields) {
			//设置些属性是可以访问的
			field.setAccessible(true);
			Column column = field.getAnnotation(Column.class);
			if(column == null) {
				continue;
			}
			if(field.getName().equals(pkKey)) {
				//为主键的列
				pkColumn = column.name();
				continue;
			}
			try {
				Object value = field.get(object);
				if(value == null) {
					//值为null则不修改
					continue;
				}
				addValue(value);
				sqlStr.append(column.name()).append("=?,");
			} catch (IllegalAccessException e) {
				throw new RuntimeException("获取属性异常!", e);
			}
		}
		sqlStr.setCharAt(sqlStr.length() - 1, ' ');
		sqlStr.append("where ").append(pkColumn).append("=?");
		addValue(pkValue);
		return sqlStr.toString();
	}
}