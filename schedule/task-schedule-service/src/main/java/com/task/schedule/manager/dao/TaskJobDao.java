package com.task.schedule.manager.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.jing.system.dao.BaseDao;
import com.jing.system.dao.sql.ModelSql;
import com.jing.system.dao.sql.QuerySql;
import com.jing.system.model.MyPage;
import com.task.schedule.manager.pojo.TaskJob;

/**
 * task_job的Dao
 * @author yuejing
 * @date 2015-03-30 14:07:27
 * @version V1.0.0
 */
@Component
public class TaskJobDao extends BaseDao {

	public void save(TaskJob taskJob) {
		super.save(taskJob);
	}

	public void update(TaskJob taskJob) {
		super.update(taskJob, "id", taskJob.getId());
	}

	public void updateWait(Integer stopStatus, Integer status) {
		super.updateSql("update task_job set status=? where status!=?", status, stopStatus);
	}
	
	public void updateStatus(Integer id, Integer status) {
		super.updateSql("update task_job set status=? where id=?", status, id);
	}

	public void delete(Integer id) {
		super.delete(TaskJob.class, "id", id);
	}

	public TaskJob get(Integer id) {
		ModelSql modelSql = new ModelSql(TaskJob.class);
		return get(modelSql.getQueryAllSql() + " where id=?", TaskJob.class, id);
	}

	public MyPage<TaskJob> pageQuery(TaskJob taskJob) {
		ModelSql modelSql = new ModelSql(TaskJob.class);
		QuerySql sql = new QuerySql(modelSql.getQueryAllSql());
		//精确查询
		sql.addCond("projectid", taskJob.getProjectid());
		//模糊查询
		sql.addCondLike(modelSql.getColumn("name"), taskJob.getName());
		//设置排序
		sql.setOrderby("id DESC");
		return pageQuery(sql.getSql(), taskJob.getPage(), taskJob.getSize(), TaskJob.class, sql.getParams());
	}

	public List<TaskJob> findByStatus(Integer status) {
		ModelSql modelSql = new ModelSql(TaskJob.class);
		return query(modelSql.getQueryAllSql() + " where status=?", TaskJob.class, status);
	}

	public List<TaskJob> findByProjectid(Integer projectid) {
		ModelSql modelSql = new ModelSql(TaskJob.class);
		return query(modelSql.getQueryAllSql() + " where projectid=?", TaskJob.class, projectid);
	}
}