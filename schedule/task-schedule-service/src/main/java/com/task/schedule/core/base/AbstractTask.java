package com.task.schedule.core.base;


/**
 * 抽象任务类
 * @author yuejing
 * @date 2015年3月29日 下午10:05:26
 * @version V1.0.0
 */
public abstract class AbstractTask implements ITask {
	
	/*public void removeJob(JobExecutionContext context) throws SchedulerException {
		Scheduler scheduler = context.getScheduler();
		Trigger trigger = context.getTrigger();
		JobDetail jobDetail = context.getJobDetail();
		scheduler.pauseTrigger(trigger.getName(), trigger.getGroup());
		scheduler.unscheduleJob(trigger.getName(), trigger.getGroup());
		scheduler.deleteJob(jobDetail.getName(), jobDetail.getGroup());
	}*/
}
