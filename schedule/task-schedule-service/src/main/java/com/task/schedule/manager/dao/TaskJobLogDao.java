package com.task.schedule.manager.dao;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.jing.system.dao.BaseDao;
import com.jing.system.dao.sql.ModelSql;
import com.jing.system.dao.sql.QuerySql;
import com.jing.system.model.MyPage;
import com.task.schedule.manager.pojo.TaskJobLog;

/**
 * task_job_log的Dao
 * @author yuejing
 * @date 2015-03-31 14:26:09
 * @version V1.0.0
 */
@Component
public class TaskJobLogDao extends BaseDao {

	public void save(TaskJobLog taskJobLog) {
		super.save(taskJobLog);
	}

	public void update(TaskJobLog taskJobLog) {
		super.update(taskJobLog, "id", taskJobLog.getId());
	}

	public void delete(Integer id) {
		super.delete(TaskJobLog.class, "id", id);
	}

	public void deleteLtDate(Date date) {
		ModelSql modelSql = new ModelSql(TaskJobLog.class);
		super.deleteSql("delete from " + modelSql.getTable() + " where addtime<?", date);
	}

	public TaskJobLog get(Integer id) {
		ModelSql modelSql = new ModelSql(TaskJobLog.class);
		return get(modelSql.getQueryAllSql() + " where id=?", TaskJobLog.class, id);
	}

	public MyPage<TaskJobLog> pageQuery(TaskJobLog taskJobLog) {
		ModelSql modelSql = new ModelSql(TaskJobLog.class);
		QuerySql sql = new QuerySql(modelSql.getQueryAllSql());
		//精确查询
		sql.addCond("jobid", taskJobLog.getJobid());
		//精确查询
		sql.addCond("status", taskJobLog.getStatus());
		//模糊查询
		//sql.addCondLike(modelSql.getColums("name"), taskJobLog.getName());
		//设置排序
		sql.setOrderby("addtime DESC");
		return pageQuery(sql.getSql(), taskJobLog.getPage(), taskJobLog.getSize(), TaskJobLog.class, sql.getParams());
	}

}
