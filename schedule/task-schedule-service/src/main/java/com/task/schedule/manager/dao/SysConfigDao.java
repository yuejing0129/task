package com.task.schedule.manager.dao;

import org.springframework.stereotype.Component;

import com.jing.system.dao.BaseDao;
import com.jing.system.dao.sql.ModelSql;
import com.jing.system.dao.sql.QuerySql;
import com.jing.system.model.MyPage;
import com.task.schedule.manager.pojo.SysConfig;

/**
 * 系统配置的Dao
 * @author yuejing
 * @date 2015-03-31 14:26:09
 * @version V1.0.0
 */
@Component
public class SysConfigDao extends BaseDao {

	public void save(SysConfig sysConfig) {
		super.save(sysConfig);
	}

	public void update(SysConfig sysConfig) {
		super.update(sysConfig, "id", sysConfig.getId());
	}

	public SysConfig get(Integer id) {
		ModelSql modelSql = new ModelSql(SysConfig.class);
		return get(modelSql.getQueryAllSql() + " where id=?", SysConfig.class, id);
	}

	public SysConfig getCode(String code) {
		ModelSql modelSql = new ModelSql(SysConfig.class);
		return get(modelSql.getQueryAllSql() + " where code=?", SysConfig.class, code);
	}

	public MyPage<SysConfig> pageQuery(SysConfig sysConfig) {
		ModelSql modelSql = new ModelSql(SysConfig.class);
		QuerySql sql = new QuerySql(modelSql.getQueryAllSql());
		//模糊查询
		//sql.addCondLike(modelSql.getColums("name"), taskJobLog.getName());
		//设置排序
		sql.setOrderby("id DESC");
		return pageQuery(sql.getSql(), sysConfig.getPage(), sysConfig.getSize(), SysConfig.class, sql.getParams());
	}

}
