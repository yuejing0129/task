package com.task.schedule.core.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

/**
 * 监听主线程
 * @author  yuejing
 * @email   yuejing0129@163.com 
 * @net		www.suyunyou.com
 * @date    2015年3月30日 下午5:46:49 
 * @version 1.0.0
 */
public class MainListener implements JobListener {

	//private static final Logger LOGGER = Logger.getLogger(MainListener.class);
	private String name;

	public MainListener(String name) {
		super();
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext inContext) {
		//执行任务前执行
		/*if(LOGGER.isInfoEnabled()) {
			LOGGER.info("Task的ID: " + name);
		}*/
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext inContext) {
		
	}

	@Override
	public void jobWasExecuted(JobExecutionContext inContext, JobExecutionException inException) {
		//任务执行完后，执行
		/*JobKey jobKey = inContext.getJobDetail().getKey();
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("Task的name: " + jobKey.getName() + "\tgroup: " + jobKey.getGroup());
		}*/

	}
}