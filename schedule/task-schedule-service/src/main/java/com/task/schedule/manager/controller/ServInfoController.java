package com.task.schedule.manager.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jing.system.model.MyPage;
import com.task.schedule.comm.controller.BaseController;
import com.task.schedule.manager.pojo.ServInfo;
import com.task.schedule.manager.service.ServInfoService;

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
