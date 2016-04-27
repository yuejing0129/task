package com.task.schedule.manager.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.task.schedule.comm.enums.ServEqStatus;
import com.task.schedule.manager.dao.ServEqDao;
import com.task.schedule.manager.pojo.ServEq;

/**
 * 服务均衡的Service
 * @author 岳静
 * @date 2016年4月25日 下午2:45:39 
 * @version V1.0
 */
@Component
public class ServEqService {

	@Autowired
	private ServEqDao servEqDao;
	@Autowired
	private SysConfigService configService;
	
	/**
	 * 增加待销毁的任务
	 * @param servid
	 * @param jobid
	 */
	public void save(String servid, Integer jobid) {
		ServEq servEq = new ServEq();
		servEq.setServid(servid);
		servEq.setJobid(jobid);
		servEq.setStatus(ServEqStatus.WAIT.getCode());
		servEqDao.save(servEq);
	}

	/**
	 * 小于指定日期的删除
	 * @param date
	 */
	public void deleteDestroyLtDate(Date date) {
		servEqDao.deleteDestroyLtDate(date);
	}

}