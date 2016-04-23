package com.task.schedule.manager.dao;

import org.springframework.stereotype.Component;

import com.jing.system.dao.BaseDao;
import com.jing.system.dao.sql.ModelSql;
import com.jing.system.dao.sql.QuerySql;
import com.jing.system.model.MyPage;
import com.task.schedule.manager.pojo.SysUser;

/**
 * sys_user的Dao
 * @author yuejing
 * @date 2015-03-30 14:07:27
 * @version V1.0.0
 */
@Component
public class SysUserDao extends BaseDao {

	public void save(SysUser sysUser) {
		Integer id = super.saveReturnKey(sysUser);
		sysUser.setId(id);
	}

	public void update(SysUser sysUser) {
		super.update(sysUser, "id", sysUser.getId());
	}

	public void updatePassword(Integer id, String password) {
		super.updateSql("update sys_user set password=? where id=?", password, id);
	}

	public void delete(Integer id) {
		super.delete(SysUser.class, "id", id);
	}

	public SysUser get(Integer id) {
		ModelSql modelSql = new ModelSql(SysUser.class);
		return get(modelSql.getQueryAllSql() + " where id=?", SysUser.class, id);
	}

	public SysUser findByUsername(String username) {
		ModelSql modelSql = new ModelSql(SysUser.class);
		return get(modelSql.getQueryAllSql() + " where username=?", SysUser.class, username);
	}

	public MyPage<SysUser> pageQuery(SysUser sysUser) {
		ModelSql modelSql = new ModelSql(SysUser.class);
		QuerySql sql = new QuerySql(modelSql.getQueryAllSql());
		//精确查询
		//sql.addCond("name", sysUser.getName());
		//模糊查询
		//sql.addCondLike(modelSql.getColums("name"), sysUser.getName());
		//设置排序
		sql.setOrderby("id DESC");
		return pageQuery(sql.getSql(), sysUser.getPage(), sysUser.getSize(), SysUser.class, sql.getParams());
	}

}