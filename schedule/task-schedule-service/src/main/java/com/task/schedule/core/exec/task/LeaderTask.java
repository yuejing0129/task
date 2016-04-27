package com.task.schedule.core.exec.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.task.schedule.comm.constants.Constant;
import com.task.schedule.comm.enums.JobStatus;
import com.task.schedule.comm.enums.ServInfoStatus;
import com.task.schedule.core.base.AbstractTask;
import com.task.schedule.manager.pojo.ServInfo;
import com.task.schedule.manager.pojo.TaskJob;
import com.task.schedule.manager.service.ServInfoService;
import com.task.schedule.manager.service.SysConfigService;
import com.task.schedule.manager.service.TaskJobService;

/**
 * Leader的定时任务类
 * @author yuejing
 * @date 2015年3月29日 下午10:05:34
 * @version V1.0.0
 */
@Component
public class LeaderTask extends AbstractTask {

	private static final Logger LOGGER = Logger.getLogger(LeaderTask.class);
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private ServInfoService servInfoService;
	@Autowired
	private TaskJobService taskJobService;

	@Override
	public void execute(JobExecutionContext context) {
		//Leader的任务
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("Leader的任务");
		}
		
		//Leader选举和获取当前Leader
		ServInfo servInfo = servInfoService.chooseLeader();
		
		if(servInfo != null && servInfo.getServid().equals(Constant.serviceCode())) {
			//自己为Leader，则分配任务
			List<TaskJob> jobs = taskJobService.findByStatus(JobStatus.NORMAL.getCode(), null);
			Map<String, List<TaskJob>> servMap = new HashMap<String, List<TaskJob>>();
			for (TaskJob job : jobs) {
				List<TaskJob> servList = servMap.get(job.getServid());
				if(servList == null) {
					servList = new ArrayList<TaskJob>();
				}
				servList.add(job);
				servMap.put(job.getServid(), servList);
			}
			//获取所有活动的服务
			List<ServInfo> list = servInfoService.findByStatus(ServInfoStatus.NORMAL.getCode());
			for (ServInfo si : list) {
				//TODO 服务负载均衡计算
				
			}
		}
	}
}