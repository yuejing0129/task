package com.jing.system.dao.sql;

import java.lang.reflect.Field;

import com.jing.system.dao.annotation.Column;
import com.jing.system.dao.annotation.Table;

/**
 * 拼接删除的sql的工具类
 * @author yuejing
 * @date 2013-11-20 下午11:51:53
 * @version V1.0.0
 */
public class DeleteSql extends Sql {

	private Class<?> cls;

	public DeleteSql(Class<?> cls, String pkKey, Object pkValue) {
		super("delete from ", pkKey, pkValue);
		this.cls = cls;
	}

	/**
	 * 获取拼接的完整的SQL
	 * @return
	 */
	public String getSql() {
		StringBuffer sqlStr = new StringBuffer(sql);
		//获取表名
		Table table = cls.getAnnotation(Table.class);
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
				break;
			}
		}
		sqlStr.append(table.name()).append(" where ").append(pkColumn).append("=?");
		addValue(pkValue);
		return sqlStr.toString();
	}
}