package com.jing.system.dao.sql;

import java.lang.reflect.Field;

import com.jing.system.dao.BaseDao;
import com.jing.system.dao.annotation.Column;
import com.jing.system.dao.annotation.Table;
import com.jing.system.utils.SpringUtil;
import com.jing.system.utils.FrameStringUtil;

/**
 * 拼接保存的sql的工具类
 * @author yuejing
 * @date 2013-11-20 下午11:51:53
 * @version V1.0.0
 */
public class InsertSql extends Sql {

	private Class<?> cls;
	private Object object;
	private Integer sequenceValue;

	/**
	 * 创建对象
	 * @param object
	 * @param pkKey		为null代表改字典不是自增长
	 */
	public InsertSql(Object object) {
		super("insert into ");
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
		sqlStr.append(table.name()).append("(");
		int paramNum = 0;
		//得到类中的所有属性集合
		Field[] fields = cls.getDeclaredFields();
		String sequenceName = null;
		//String sequenceColumn = null;
		for (Field field : fields) {
			//设置些属性是可以访问的
			field.setAccessible(true);
			Column column = field.getAnnotation(Column.class);
			if(column == null) {
				continue;
			}
			if(column.isAuto()) {
				//为自增长
				continue;
			}
			sqlStr.append(column.name()).append(",");
			try {
				if(FrameStringUtil.isNotEmpty(column.sequence())) {
					//存在序列（放列名，不放值）
					sequenceName = column.sequence();
					//sequenceColumn = column.name();
					BaseDao baseDao = SpringUtil.getBean("baseDao");
					sequenceValue = baseDao.queryForLong("select " + sequenceName + ".nextVal from dual").intValue();
					field.set(object, sequenceValue);
					addValue(sequenceValue);
				} else {
					addValue(field.get(object));
				}
			} catch (IllegalAccessException e) {
				throw new RuntimeException("获取属性异常!", e);
			}
			paramNum ++;
		}
		/*if(StringUtil.isNotEmpty(sequenceColumn) && DbUtil.isOracle()) {
			//有序列
			sqlStr.append(sequenceColumn).append(",");
		}*/
		sqlStr.setCharAt(sqlStr.length() - 1, ')');
		sqlStr.append(" values(");
		for(int i = 0; i < paramNum; i ++) {
			sqlStr.append("?,");
		}
		/*if(StringUtil.isNotEmpty(sequenceName) && DbUtil.isOracle()) {
			//存在序列，并且数据库为Oracle
			sqlStr.append(sequenceName).append(".nextVal,");
		}*/
		sqlStr.setCharAt(sqlStr.length() - 1, ')');
		return sqlStr.toString();
	}
	
	public Integer getSequenceValue() {
		return this.sequenceValue;
	}
}