package com.task.schedule.manager.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jing.system.model.MyPage;
import com.jing.system.utils.FrameJsonUtil;
import com.task.schedule.comm.controller.BaseController;
import com.task.schedule.comm.enums.ServInfoStatus;
import com.task.schedule.manager.pojo.ServInfo;
import com.task.schedule.manager.service.ServInfoService;
import com.task.schedule.manager.service.TaskJobService;

/**
 * 服务的Controller
 * @author yuejing
 * @date 2015-03-30 14:07:28
 * @version V1.0.0
 */
@Controller
public class ServInfoController extends BaseController {

	private static final Logger logger = Logger.getLogger(ServInfoController.class);

	@Autowired
	private ServInfoService servInfoService;
	@Autowired
	private TaskJobService taskJobService;
	
	/**
	 * 跳转到管理页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/servInfo/f_view/manager")
	public String manger(HttpServletRequest request) {
		return "manager/serv/servInfo_manager";
	}
	/**
	 * 跳转到图表页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/servInfo/f_view/chart")
	public String chart(HttpServletRequest request, ModelMap modelMap) {
		List<ServInfo> servInfos = servInfoService.findByStatus(ServInfoStatus.NORMAL.getCode());
		for (ServInfo si : servInfos) {
			//设置任务总数
			int jobnum = taskJobService.getCountByServid(si.getServid());
			si.setJobnum(jobnum);
		}
		modelMap.put("servInfosJson", FrameJsonUtil.toString(servInfos));
		return "manager/serv/servInfo_chart";
	}

	/**
	 * 分页获取信息
	 * @return
	 */
	@RequestMapping(value = "/servInfo/f_json/pageQuery")
	@ResponseBody
	public MyPage<ServInfo> pageQuery(HttpServletRequest request, ServInfo servInfo) {
		MyPage<ServInfo> page = null;
		try {
			page = servInfoService.pageQuery(servInfo);
		} catch (Exception e) {
			logger.error("分页获取信息异常: " + e.getMessage(), e);
		}
		return page;
	}
}
