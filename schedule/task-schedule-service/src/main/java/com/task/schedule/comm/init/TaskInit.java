package com.task.schedule.comm.init;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.jing.system.utils.DateUtil;
import com.jing.system.utils.FrameSpringBeanUtil;
import com.task.schedule.comm.constants.Constant;
import com.task.schedule.comm.constants.DictCons;
import com.task.schedule.core.exec.TaskManager;

/**
 * 初始化系统数据的Servlet
 * @author yuejing
 * @date 2013-8-16 下午9:54:12
 * @version V1.0.0
 */
public class TaskInit extends HttpServlet {

	private static final Logger logger = Logger.getLogger(TaskInit.class);

	private static final long serialVersionUID = -2274726206362496315L;

	/**
	 * 初始化方法
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		logger.info("初始化数据中...");
		long startTime = System.currentTimeMillis();
		Constant.webroot = config.getServletContext().getContextPath();
		config.getServletContext().setAttribute("webroot", Constant.webroot);
		//版本号为年月日[如: 20130126]
		String version = String.format("?version=%s", DateUtil.dateToString(DateUtil.getTime(), DateUtil.FMT_YYYYMMDDHH));
		config.getServletContext().setAttribute("version", version);
		
		//初始化字典信息
		DictCons.init(config.getServletContext());
		
		//添加定时任务
		TaskManager taskManager = (TaskManager)FrameSpringBeanUtil.getBean(TaskManager.class);
		taskManager.init();
		
		logger.info("初始化资源花费" + (System.currentTimeMillis() - startTime) + "毫秒!");
	}

}
