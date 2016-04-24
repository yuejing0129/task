package com.task.schedule.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.task.schedule.manager.pojo.TaskJob;

/**
 * task_job的Dao
 * @author yuejing
 * @date 2015-03-30 14:07:27
 * @version V1.0.0
 */
public interface TaskJobDao {

	public void save(TaskJob taskJob);

	public void update(TaskJob taskJob);

	public void updateWait(@Param("stopStatus")Integer stopStatus, @Param("status")Integer status);
	
	public void updateStatus(@Param("id")Integer id, @Param("status")Integer status);

	public void delete(@Param("id")Integer id);

	public TaskJob get(@Param("id")Integer id);

	public List<TaskJob> findByStatus(@Param("status")Integer status);

	public List<TaskJob> findByProjectid(@Param("projectid")Integer projectid);

	public List<TaskJob> findTaskJob(TaskJob taskJob);
	public int findTaskJobCount(TaskJob taskJob);
}