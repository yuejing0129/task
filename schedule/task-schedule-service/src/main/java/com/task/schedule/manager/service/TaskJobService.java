package com.task.schedule.manager.service;

import java.util.List;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.task.schedule.comm.enums.Boolean;
import com.task.schedule.comm.enums.JobStatus;
import com.task.schedule.core.exec.JobService;
import com.task.schedule.manager.dao.TaskJobDao;
import com.task.schedule.manager.pojo.TaskJob;
import com.jing.system.model.MyPage;
import com.jing.system.utils.DateUtil;

/**
 * task_job的Service
 * @author yuejing
 * @date 2015-03-30 14:07:27
 * @version V1.0.0
 */
@Component
public class TaskJobService {

	@Autowired
	private TaskJobDao taskJobDao;
	@Autowired
	private JobService jobService;
	
	/**
	 * 保存
	 * @param taskJob
	 */
	public void save(TaskJob taskJob) {
		if(taskJob.getStatus().intValue() != JobStatus.STOP.getCode().intValue()) {
			taskJob.setStatus(JobStatus.WAIT.getCode());
		}
		if(taskJob.getIsfailmail() == null) {
			taskJob.setIsfailmail(Boolean.FALSE.getCode());
		}
		taskJob.setAddtime(DateUtil.getTime());
		taskJobDao.save(taskJob);
	}

	/**
	 * 删除
	 * @param id
	 * @throws SchedulerException 
	 */
	public void delete(Integer id) throws SchedulerException {
		jobService.deleteJob(id.toString());
		taskJobDao.delete(id);
	}
	
	public void deleteProjectid(Integer projectid) throws SchedulerException {
		List<TaskJob> list = findByProjectid(projectid);
		for (TaskJob taskJob : list) {
			delete(taskJob.getId());
		}
	}

	private List<TaskJob> findByProjectid(Integer projectid) {
		return taskJobDao.findByProjectid(projectid);
	}

	/**
	 * 修改
	 * @param taskJob
	 * @throws SchedulerException 
	 */
	public void update(TaskJob taskJob) throws SchedulerException {
		if(taskJob.getStatus().intValue() == JobStatus.STOP.getCode().intValue()) {
			//状态为停止，则删除任务
			jobService.deleteJob(taskJob.getId().toString());
		} else {
			//状态不为停止，则启动任务
			taskJob.setStatus(JobStatus.WAIT.getCode());
		}
		taskJobDao.update(taskJob);
	}

	/**
	 * 修改任务状态
	 * @param id
	 * @param status
	 */
	public void updateStatus(Integer id, Integer status) {
		taskJobDao.updateStatus(id, status);
	}

	/**
	 * 根据id获取对象
	 * @param id
	 * @return
	 */
	public TaskJob get(Integer id) {
		return taskJobDao.get(id);
	}

	/**
	 * 分页获取对象
	 * @param taskJob
	 * @return
	 */
	public MyPage<TaskJob> pageQuery(TaskJob taskJob) {
		MyPage<TaskJob> page = taskJobDao.pageQuery(taskJob);
		for (TaskJob job : page.getRows()) {
			job.setStatusname(JobStatus.getText(job.getStatus()));
			job.setIsfailmailname(Boolean.getText(job.getIsfailmail()));
		}
		return page;
	}

	/**
	 * 修改除停止的任务外为待添加
	 * @param status
	 */
	public void updateWait(Integer status) {
		taskJobDao.updateWait(JobStatus.STOP.getCode(), status);
	}

	/**
	 * 根据状态获取任务列表
	 * @param status
	 * @return
	 */
	public List<TaskJob> findByStatus(Integer status) {
		return taskJobDao.findByStatus(status);
	}
}