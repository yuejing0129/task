package com.task.schedule.manager.dao;

import org.springframework.stereotype.Component;

import com.jing.system.dao.BaseDao;
import com.jing.system.dao.sql.ModelSql;
import com.jing.system.dao.sql.QuerySql;
import com.jing.system.model.MyPage;
import com.task.schedule.manager.pojo.TaskProject;

/**
 * task_project的Dao
 * @author yuejing
 * @date 2015-03-30 14:07:28
 * @version V1.0.0
 */
@Component
public class TaskProjectDao extends BaseDao {

	public void save(TaskProject taskProject) {
		super.save(taskProject);
	}

	public void update(TaskProject taskProject) {
		super.update(taskProject, "id", taskProject.getId());
	}

	public void delete(Integer id) {
		super.delete(TaskProject.class, "id", id);
	}

	public TaskProject get(Integer id) {
		ModelSql modelSql = new ModelSql(TaskProject.class);
		return get(modelSql.getQueryAllSql() + " where id=?", TaskProject.class, id);
	}

	public MyPage<TaskProject> pageQuery(TaskProject taskProject) {
		ModelSql modelSql = new ModelSql(TaskProject.class);
		QuerySql sql = new QuerySql(modelSql.getQueryAllSql());
		//精确查询
		//sql.addCond("name", taskProject.getName());
		//模糊查询
		sql.addCondLike(modelSql.getColumn("name"), taskProject.getName());
		//设置排序
		sql.setOrderby("id DESC");
		return pageQuery(sql.getSql(), taskProject.getPage(), taskProject.getSize(), TaskProject.class, sql.getParams());
	}

}
