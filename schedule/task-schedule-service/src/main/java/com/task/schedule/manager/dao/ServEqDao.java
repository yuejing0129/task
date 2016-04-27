package com.task.schedule.manager.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.task.schedule.manager.pojo.ServEq;

public interface ServEqDao {

	public abstract void save(ServEq servEq);

	public abstract void deleteDestroyLtDate(@Param("date")Date date);
}