package com.task.schedule.core.exec.task;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.task.schedule.comm.enums.JobStatus;
import com.task.schedule.core.base.AbstractTask;
import com.task.schedule.core.exec.JobService;
import com.task.schedule.core.listener.TaskJobListener;
import com.task.schedule.manager.pojo.TaskJob;
import com.task.schedule.manager.service.TaskJobService;

/**
 * 系统公用的定时任务类
 * @author yuejing
 * @date 2015年3月29日 下午10:05:34
 * @version V1.0.0
 */
@Component
public class MainTask extends AbstractTask {

	private static final Logger LOGGER = Logger.getLogger(MainTask.class);
	@Autowired
	private JobService jobService;
	@Autowired
	private TaskJobService taskJobService;
	@Autowired
	private TaskJobTask taskJobTask;

	@Override
	public void execute(JobExecutionContext context) {
		//获取所有没有加入的任务
		List<TaskJob> jobs = taskJobService.findByStatus(JobStatus.WAIT.getCode());
		for (TaskJob taskJob : jobs) {
			try {
				/*if(taskJobService.get(taskJob.getId()).getStatus().intValue() != JobStatus.WAIT.getCode().intValue()) {
					continue;
				}*/
				jobService.addOrUpdateJob(taskJob.getId().toString(), taskJob.getCron(), taskJobTask, new TaskJobListener(taskJob.getId().toString()));
				LOGGER.info("主线程添加任务 ID【" + taskJob.getId() + "】名称【" + taskJob.getName() + "】成功");
				//修改状态为正常
				taskJobService.updateStatus(taskJob.getId(), JobStatus.NORMAL.getCode());
			} catch (SchedulerException e) {
				LOGGER.error(e.getMessage(), e);
				taskJobService.updateStatus(taskJob.getId(), JobStatus.ERROR_ADD.getCode());
			}
		}
	}
}