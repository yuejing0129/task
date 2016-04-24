package com.task.schedule.core.exec.task;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.task.schedule.comm.constants.Constant;
import com.task.schedule.comm.enums.JobStatus;
import com.task.schedule.core.base.AbstractTask;
import com.task.schedule.core.exec.JobService;
import com.task.schedule.core.listener.TaskJobListener;
import com.task.schedule.manager.pojo.TaskJob;
import com.task.schedule.manager.service.TaskJobService;

/**
 * 集群任务调度线程的定时任务类
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
		
		//=========================== 获取待执行的任务和检测到updatetime小于(当前时间-30s)的任务 begin ====================
		//将任务执行过期30s和状态不为停止的 改为加入待执行
		taskJobService.updateWait();
		
		//修改指定数目为自己的服务
		taskJobService.updateServidByWait(Constant.serviceCode(), Constant.TASK_JOB_WAIT_NUM);
		
		//获取所有没有加入的任务-每次获取3个
		List<TaskJob> jobs = taskJobService.findByServidStatus(Constant.serviceCode(), JobStatus.WAIT.getCode());
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
		//=========================== 获取待执行的任务和检测到updatetime小于(当前时间-30s)的任务 end ====================

		//=========================== 发送任务心跳（间隔10s） begin ====================
		
		//修改当前服务的updatetime为当前时间
		taskJobService.updateUpdatetimeByServidStatus(Constant.serviceCode(), JobStatus.NORMAL.getCode());
		
		//=========================== 发送任务心跳（间隔10s） end ====================
		

		//=========================== 检测quartz执行的任务 begin ====================
		
		//任务是否还和自己绑定, 不是则删除quartz里面的任务 TODO 获取quartz的所有任务
		
		//=========================== 检测quartz执行的任务 end ====================
	}
}