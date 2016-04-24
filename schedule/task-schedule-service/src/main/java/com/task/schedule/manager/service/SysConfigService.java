package com.task.schedule.manager.service;

import java.util.List;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jing.system.model.MyPage;
import com.task.schedule.comm.constants.Constant;
import com.task.schedule.comm.enums.Config;
import com.task.schedule.core.exec.JobService;
import com.task.schedule.core.exec.task.MainTask;
import com.task.schedule.core.exec.task.TaskJobLogCleanTask;
import com.task.schedule.core.listener.MainListener;
import com.task.schedule.manager.dao.SysConfigDao;
import com.task.schedule.manager.pojo.SysConfig;

/**
 * 系统配置的Service
 * @author yuejing
 * @date 2015-03-30 14:07:27
 * @version V1.0.0
 */
@Component
public class SysConfigService {

	@Autowired
	private SysConfigDao sysConfigDao;
	@Autowired
	private JobService jobService;
	@Autowired
	private MainTask mainTask;
	@Autowired
	private TaskJobLogCleanTask taskJobLogCleanTask;

	/**
	 * 保存
	 * @param sysConfig
	 */
	public void save(SysConfig sysConfig) {
		sysConfigDao.save(sysConfig);
	}

	/**
	 * 修改
	 * @param sysUser
	 * @throws SchedulerException 
	 */
	public void update(SysConfig sysConfig) throws SchedulerException {
		sysConfigDao.update(sysConfig);
		if(Config.TASK_MAIN_CRON.getCode().equals(sysConfig.getCode())) {
			//为修改主线程的配置
			jobService.addOrUpdateJob(Constant.TASK_ID_MAIN, sysConfig.getValue(), mainTask, new MainListener(Constant.TASK_ID_MAIN));
		} else if(Config.JOBLOG_CLEAN_CRON.getCode().equals(sysConfig.getCode())) {
			//修改清除日志任务的配置
			jobService.addOrUpdateJob(Constant.TASK_ID_TASKJOBLOG_CLEAN, sysConfig.getValue(), taskJobLogCleanTask, new MainListener(Constant.TASK_ID_TASKJOBLOG_CLEAN));
		}
	}

	/**
	 * 根据id获取对象
	 * @param id
	 * @return
	 */
	public SysConfig get(Integer id) {
		return sysConfigDao.get(id);
	}

	/**
	 * 根据code获取名称
	 * @param id
	 * @return
	 */
	public String getCode(Config config) {
		SysConfig sysConfig = sysConfigDao.getByCode(config.getCode());
		if(sysConfig == null) {
			return null;
		}
		return sysConfig.getValue();
	}

	/**
	 * 分页获取对象
	 * @param sysUser
	 * @return
	 */
	public MyPage<SysConfig> pageQuery(SysConfig sysConfig) {
		sysConfig.setDefPageSize();
		int total = sysConfigDao.findSysConfigCount(sysConfig);
		List<SysConfig> rows = null;
		if(total > 0) {
			rows = sysConfigDao.findSysConfig(sysConfig);
		}
		MyPage<SysConfig> page = new MyPage<SysConfig>(sysConfig.getPage(), sysConfig.getSize(), total, rows);
		return page;
	}

}