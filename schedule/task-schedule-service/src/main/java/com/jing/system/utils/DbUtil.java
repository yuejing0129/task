package com.jing.system.utils;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

public class DbUtil {

	private static final Logger LOGGER = Logger.getLogger(DbUtil.class);

	public static final String DB_MYSQL = "mysql";
	public static final String DB_ORACLE = "oracle";
	public static String dbType = null;
	public static String dbVersion = null;

	public static boolean isMysql() {
		return isType(DB_MYSQL);
	}

	public static boolean isOracle() {
		return isType(DB_ORACLE);
	}
	
	/**
	 * 根据传入的类型判断数据库类型
	 * @param dbType
	 * @return
	 */
	private static boolean isType(String dbType) {
		if(DbUtil.dbType == null) {
			JdbcTemplate jdbcTemplate = SpringUtil.getBean(JdbcTemplate.class);
			try {
				DatabaseMetaData md = jdbcTemplate.getDataSource().getConnection().getMetaData();  
				DbUtil.dbType = md.getDatabaseProductName().toLowerCase();  
				DbUtil.dbVersion = md.getDatabaseProductVersion();
			} catch (SQLException e) {
				LOGGER.error("获取数据库类型异常：" + e.getLocalizedMessage(), e);
			}
			if(LOGGER.isInfoEnabled()) {
				LOGGER.info("数据库类型为：" + DbUtil.dbType);
			}
		}
		return dbType.equals(DbUtil.dbType);
	}
}