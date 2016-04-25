package com.task.schedule.manager.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.task.schedule.manager.pojo.ServInfo;

public interface ServInfoDao {

	public abstract void save(ServInfo servInfo);

	public abstract void delete(@Param("servid")String servid);

	public abstract ServInfo get(@Param("servid")Integer servid);

	public abstract List<ServInfo> findServInfo(ServInfo servInfo);
	public abstract int findServInfoCount(ServInfo servInfo);

	public abstract void updateUpdatetimeByServid(@Param("servid")String servid);

	public abstract void updateDestroy(@Param("destroyStatus")Integer destroyStatus, @Param("destroyTime")Integer destroyTime);

	public abstract void deleteDestroyLtDate(@Param("destroyStatus")Integer destroyStatus, @Param("date")Date date);
}