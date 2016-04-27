package com.task.schedule.manager.service;

import java.util.List;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jing.system.model.MyPage;
import com.task.schedule.comm.enums.Boolean;
import com.task.schedule.comm.enums.Config;
import com.task.schedule.comm.enums.JobStatus;
import com.task.schedule.core.exec.JobService;
import com.task.schedule.manager.dao.TaskJobDao;
import com.task.schedule.manager.pojo.TaskJob;

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
	@Autowired
	private SysConfigService configService;
	
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
			//状态为停止，则删除任务，在MainTask线程会删除任务
			jobService.deleteJob(taskJob.getId().toString());
		} else {
			//状态不为停止，则启动任务，在MainTask线程会删除任务
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
		taskJob.setDefPageSize();
		int total = taskJobDao.findTaskJobCount(taskJob);
		List<TaskJob> rows = null;
		if(total > 0) {
			rows = taskJobDao.findTaskJob(taskJob);
			for (TaskJob job : rows) {
				job.setStatusname(JobStatus.getText(job.getStatus()));
				job.setIsfailmailname(Boolean.getText(job.getIsfailmail()));
			}
		}
		MyPage<TaskJob> page = new MyPage<TaskJob>(taskJob.getPage(), taskJob.getSize(), total, rows);
		return page;
	}

	/**
	 * 将任务执行过期30s和状态不为停止的 改为加入待执行
	 */
	public void updateWait() {
		Integer destroyTime = Integer.valueOf(configService.getValue(Config.LOCK_DESTROY_TIME, "30"));
		taskJobDao.updateWait(JobStatus.WAIT.getCode(), JobStatus.STOP.getCode(), destroyTime);
	}

	/**
	 * 根据状态获取任务列表
	 * @param status
	 * @param topnum
	 * @return
	 */
	public List<TaskJob> findByStatus(Integer status, Integer topnum) {
		return taskJobDao.findByStatus(status, topnum);
	}

	/**
	 * 获取非停止的任务
	 * @param status
	 * @param topnum
	 * @return
	 */
	public List<TaskJob> findActive() {
		return taskJobDao.findActive(JobStatus.NORMAL.getCode(), JobStatus.WAIT.getCode());
	}

	/**
	 * 修改该服务待添加的任务
	 * @param servid
	 * @param topnum
	 */
	public void updateServidByWait(String servid, Integer topnum) {
		taskJobDao.updateByServid(JobStatus.WAIT.getCode(), servid, topnum);
	}

	/**
	 * 获取服务待执行的任务
	 * @param servid
	 * @param status
	 * @return
	 */
	public List<TaskJob> findByServidStatus(String servid, Integer status) {
		return taskJobDao.findByServidStatus(servid, status);
	}

	/**
	 * 根据服务和状态修改updatetime为当前时间
	 * @param servid
	 * @param status
	 */
	public void updateUpdatetimeByServidStatus(String servid, Integer status) {
		taskJobDao.updateUpdatetimeByServidStatus(servid, status);
	}
}