package com.jing.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.jing.system.dao.sql.DeleteSql;
import com.jing.system.dao.sql.InsertSql;
import com.jing.system.dao.sql.Sql;
import com.jing.system.dao.sql.UpdateSql;
import com.jing.system.model.MyPage;
import com.jing.system.utils.DbUtil;
import com.jing.system.utils.SpringUtil;

@Component
public class BaseDao {

	private static final Logger LOGGER = Logger.getLogger(BaseDao.class);
	
	//采用该方法是为了兼容在其它项目中没有使用SpringJdbcTemplate的情况
	private JdbcTemplate getJdbcTemplate() {
		return SpringUtil.getBean(JdbcTemplate.class);
	}

	/**
	 * 保存[不返回自增长的主键]
	 * @param sql
	 * @param params	动态参数[对应where条件中的?]
	 */
	public int saveSql(final String sql, final Object ...params){
		return getJdbcTemplate().update(sql, params);
	}

	/**
	 * 保存
	 * @param object
	 */
	public int save(Object object) {
		Sql sql = new InsertSql(object);
		return saveSql(sql.getSql(), sql.getParams());
	}

	/**
	 * 保存返回生成的主键
	 * @param object
	 * @return
	 */
	public Integer saveReturnKey(Object object) {
		InsertSql sql = new InsertSql(object);
		if(DbUtil.isMysql()) {
			return saveReturnKey(sql.getSql(), sql.getParams());
		} else if(DbUtil.isOracle()) {
			saveSql(sql.getSql(), sql.getParams());
			return sql.getSequenceValue();
		}
		throw new RuntimeException("目前不支持该数据库类型");
	}

	/**
	 * 保存[返回自增长的主键] [下个版本将会保密]
	 * @param sql
	 * @param params
	 * @return
	 */
	@Deprecated
	public Integer saveReturnKey(final String sql, final Object ...params){
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				for (int i = 0; i < params.length; i ++) {
					ps.setObject( (i + 1), params[i]);
				}
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	/**
	 * 保存或修改
	 * @param object
	 * @param pkKey
	 * @param pkValue
	 */
	public void saveOrUpdate(Object object, String pkKey, Object pkValue) {
		if(pkValue == null) {
			Sql sql = new InsertSql(object);
			saveSql(sql.getSql(), sql.getParams());
		} else {
			Sql sql = new UpdateSql(object, pkKey, pkValue);
			updateSql(sql.getSql(), sql.getParams());
		}
	}

	/**
	 * 删除
	 * @param sql
	 * @param params	动态参数[对应where条件中的?]
	 */
	public int deleteSql(final String sql, final Object ...params){
		return getJdbcTemplate().update(sql, params);
	}

	/**
	 * 根据主键删除记录
	 * @param cls
	 * @param pkKey
	 * @param pkValue
	 */
	public int delete(Class<?> cls, String pkKey, Object pkValue) {
		Sql sql = new DeleteSql(cls, pkKey, pkValue);
		return deleteSql(sql.getSql(), sql.getParams());
	}

	/**
	 * 修改
	 * @param sql
	 * @param params
	 */
	public int updateSql(final String sql, final Object ...params) {
		return getJdbcTemplate().update(sql, params);
	}

	/**
	 * 根据主键修改对象
	 * @param object
	 * @param pkKey
	 * @param pkValue
	 * @return
	 */
	public int update(Object object, String pkKey, Object pkValue) {
		Sql sql = new UpdateSql(object, pkKey, pkValue);
		return updateSql(sql.getSql(), sql.getParams());
	}

	/**
	 * 删除表中所有记录[下个版本将会被删除]
	 * @param table
	 */
	@Deprecated
	public int deleteAll(final String table) {
		StringBuffer sql = new StringBuffer("DELETE FROM ").append(table);
		return getJdbcTemplate().update(sql.toString());
	}

	/**
	 * 查询对象
	 * @param <T>
	 * @param sql
	 * @param object
	 * @param params	动态参数[对应where条件中的?]
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T>T get(final String sql, final Class cls, final Object ...params) {
		try {
			return (T) getJdbcTemplate().queryForObject(sql, params, new BeanPropertyRowMapper(cls));
		} catch (CannotGetJdbcConnectionException e) {
			throw new RuntimeException(e.getLocalizedMessage(), e);
		} catch (DataAccessException e) {
			if(LOGGER.isInfoEnabled()) {
				LOGGER.info("get方法没有获取到记录返回null");
			}
			return null;
		}
	}
	
	/**
	 * 获取Long类型的值
	 * @param sql
	 * @param params
	 * @return
	 */
	public Long queryForLong(String sql, Object ...params) {
		return getJdbcTemplate().queryForLong(sql, params);
	}

	/**
	 * 查询List集合
	 * @param sql
	 * @param object
	 * @param params	动态参数[对应where条件中的?]
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> query(final String sql, final Class c, final Object ...params){
		return getJdbcTemplate().query(sql, params, new BeanPropertyRowMapper(c));
	}

	/**
	 * 查询函数统计出来的值[如带有count(*)/sum(num)]
	 * @param sql
	 * @param params	动态参数[对应where条件中的?]
	 * @return
	 */
	public Integer queryFunc(final String sql, final Object ...params) {
		try{
			return getJdbcTemplate().queryForInt(sql, params);
		} catch (CannotGetJdbcConnectionException e) {
			throw new RuntimeException(e.getLocalizedMessage(), e);
		} catch(DataAccessException e) {
			return 0;
		}
	}

	/**
	 * Mysql分页查询
	 * @param <T>
	 * @param sql
	 * @param page
	 * @param size
	 * @param cls
	 * @param params
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public <T> MyPage<T> pageQuery(final String sql, final Integer page, final Integer size, final Class cls, final Object ...params){
		if(DbUtil.isMysql()) {
			return pageQueryMysql(sql, page, size, cls, params);
		} else if(DbUtil.isOracle()) {
			return pageQueryOracle(sql, page, size, cls, params);
		}
		return null;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <T> MyPage<T> pageQueryOracle(final String sql, final Integer page, final Integer size, final Class cls, final Object ...params){
		StringBuffer pageSql = new StringBuffer("select * from ( select a.*, rownum rn from (");
		pageSql.append(sql);
		int orclBegNum = 0;
		int orclEndNum = 0;
		if(page != null && size != null) {
			orclBegNum = ( (page - 1) * size) + 1;
		}
		if(page != null && size != null) {
			orclEndNum = page * size;
		}
		pageSql.append(") a where rownum <="+orclEndNum+") where rn>=").append(orclBegNum);
		List<?> list = getJdbcTemplate().query(pageSql.toString(), params, new BeanPropertyRowMapper(cls));
		return (MyPage<T>) setMyPageOracle(sql.toString(), list, page, size, params);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private MyPage<?> setMyPageOracle(String sql, List<?> data, Integer page, Integer size, Object ...params) {
		String countSql = null;
		if(sql.indexOf("from") != -1){
			countSql = sql.substring(sql.indexOf("from"));
		}else if(sql.indexOf("FROM") != -1){
			countSql = sql.substring(sql.indexOf("FROM"));
		}
		if(countSql.contains("ORDER BY"))
			countSql = countSql.substring(0, countSql.indexOf("ORDER BY"));
		else if(countSql.contains("order by"))
			countSql = countSql.substring(0, countSql.indexOf("order by"));
		//查询总的记录条数
		Integer total = queryFunc("select count(*) " + countSql, params);
		return new MyPage(page, size, total, data);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <T> MyPage<T> pageQueryMysql(final String sql, final Integer page, final Integer size, final Class cls, final Object ...params){
		StringBuffer pageSql = new StringBuffer(sql);
		String sqlStr = sql.toString();
		pageSql.append(" LIMIT ").append((page - 1) * size).append(",").append(size);
		List<?> list = getJdbcTemplate().query(pageSql.toString(), params, new BeanPropertyRowMapper(cls));
		return (MyPage<T>) setMyPageMysql(sqlStr.toString(), list, page, size, params);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private MyPage<?> setMyPageMysql(String sql, List<?> data, Integer page, Integer size, Object ...params) {
		String countSql = null;
		if(sql.indexOf("from") != -1){
			countSql = sql.substring(sql.indexOf("from"));
		}else if(sql.indexOf("FROM") != -1){
			countSql = sql.substring(sql.indexOf("FROM"));
		}
		if(countSql.contains("ORDER BY"))
			countSql = countSql.substring(0, countSql.indexOf("ORDER BY"));
		else if(countSql.contains("order by"))
			countSql = countSql.substring(0, countSql.indexOf("order by"));
		//查询总的记录条数
		Integer total = queryFunc("select count(*) " + countSql, params);
		return new MyPage(page, size, total, data);
	}
}