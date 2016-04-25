package com.task.schedule.manager.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jing.system.model.MyPage;
import com.jing.system.utils.FrameAddressUtil;
import com.task.schedule.comm.constants.Constant;
import com.task.schedule.comm.enums.Config;
import com.task.schedule.comm.enums.ServInfoStatus;
import com.task.schedule.manager.dao.ServInfoDao;
import com.task.schedule.manager.pojo.ServInfo;

/**
 * 服务的Service
 * @author 岳静
 * @date 2016年4月25日 下午2:45:39 
 * @version V1.0
 */
@Component
public class ServInfoService {

	@Autowired
	private ServInfoDao servInfoDao;
	@Autowired
	private SysConfigService configService;
	
	/**
	 * 注册服务
	 */
	public void registerServer() {
		ServInfo servInfo = new ServInfo();
		servInfo.setServid(Constant.serviceCode());
		servInfo.setIp(FrameAddressUtil.getLocalIP());
		servInfo.setStatus(ServInfoStatus.NORMAL.getCode());
		servInfoDao.save(servInfo);
	}

	/**
	 * 删除
	 * @param servid
	 */
	public void delete(String servid) {
		servInfoDao.delete(servid);
	}

	/**
	 * 根据servid获取对象
	 * @param servid
	 * @return
	 */
	public ServInfo get(Integer servid) {
		return servInfoDao.get(servid);
	}

	/**
	 * 分页获取对象
	 * @param servInfo
	 * @return
	 */
	public MyPage<ServInfo> pageQuery(ServInfo servInfo) {
		servInfo.setDefPageSize();
		int total = servInfoDao.findServInfoCount(servInfo);
		List<ServInfo> rows = null;
		if(total > 0) {
			rows = servInfoDao.findServInfo(servInfo);
			for (ServInfo si : rows) {
				si.setStatusname(ServInfoStatus.getText(si.getStatus()));
			}
		}
		MyPage<ServInfo> page = new MyPage<ServInfo>(servInfo.getPage(), servInfo.getSize(), total, rows);
		return page;
	}

	/**
	 * 修改服务的updatetime为当前时间
	 * @param servid
	 */
	public void updateUpdatetimeByServid(String servid) {
		servInfoDao.updateUpdatetimeByServid(servid);
	}

	/**
	 * 将过期的服务状态变更为停止
	 */
	public void updateDestroy() {
		Integer destroyTime = Integer.valueOf(configService.getValue(Config.LOCK_DESTROY_TIME, "30"));
		servInfoDao.updateDestroy(ServInfoStatus.DESTROY.getCode(), destroyTime);
	}

	public void deleteDestroyLtDate(Date date) {
		servInfoDao.deleteDestroyLtDate(ServInfoStatus.DESTROY.getCode(), date);
	}

}