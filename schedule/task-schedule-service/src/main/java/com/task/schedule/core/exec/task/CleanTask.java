package com.task.schedule.core.exec.task;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jing.system.utils.DateUtil;
import com.task.schedule.comm.enums.Config;
import com.task.schedule.core.base.AbstractTask;
import com.task.schedule.manager.service.ServInfoService;
import com.task.schedule.manager.service.SysConfigService;
import com.task.schedule.manager.service.TaskJobLogService;

/**
 * 清空任务日志的定时任务类
 * @author yuejing
 * @date 2015年3月29日 下午10:05:34
 * @version V1.0.0
 */
@Component
public class CleanTask extends AbstractTask {

	private static final Logger LOGGER = Logger.getLogger(CleanTask.class);
	@Autowired
	private TaskJobLogService taskJobLogService;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private ServInfoService servInfoService;

	@Override
	public void execute(JobExecutionContext context) {
		//清空小于指定日期的日志
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("清空小于指定日期日志的定时任务");
		}
		String value = sysConfigService.getValue(Config.JOBLOG_SAVE_DAY, "7");
		Date date = DateUtil.addDays(DateUtil.getTime(), - Integer.valueOf(value));
		taskJobLogService.deleteLtDate(date);
		
		//清空小于指定日期的已停止的服务
		servInfoService.deleteDestroyLtDate(date);
	}
}