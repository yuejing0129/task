package com.task.schedule.manager.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jing.system.model.MyPage;
import com.jing.system.utils.JsonUtil;
import com.task.schedule.comm.controller.BaseController;
import com.task.schedule.comm.enums.ProjectSign;
import com.task.schedule.manager.pojo.SysUser;
import com.task.schedule.manager.pojo.TaskProject;
import com.task.schedule.manager.service.TaskProjectService;

/**
 * task_project的Controller
 * @author yuejing
 * @date 2015-03-30 14:07:28
 * @version V1.0.0
 */
@Controller
public class TaskProjectController extends BaseController {

	private static final Logger logger = Logger.getLogger(TaskProjectController.class);

	@Autowired
	private TaskProjectService taskProjectService;
	
	/**
	 * 跳转到管理页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/taskProject/f_view/manager")
	public String manger(HttpServletRequest request) {
		return "manager/task/project_manager";
	}

	/**
	 * 分页获取信息
	 * @return
	 */
	@RequestMapping(value = "/taskProject/f_json/pageQuery")
	@ResponseBody
	public MyPage<TaskProject> pageQuery(HttpServletRequest request, TaskProject taskProject) {
		MyPage<TaskProject> page = null;
		try {
			page = taskProjectService.pageQuery(taskProject);
		} catch (Exception e) {
			logger.error("分页获取信息异常: " + e.getMessage(), e);
		}
		return page;
	}

	/**
	 * 跳转到编辑页[包含新增和编辑]
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/taskProject/f_view/edit")
	public String edit(HttpServletRequest request, ModelMap modelMap, Integer id) {
		if(id != null) {
			modelMap.put("taskProject", taskProjectService.get(id));
		}
		modelMap.put("projectSigns", JsonUtil.toString(ProjectSign.getList()));
		return "manager/task/project_edit";
	}

	/**
	 * 保存
	 * @return
	 */
	@RequestMapping(value = "/taskProject/f_json/save")
	@ResponseBody
	public ModelMap save(HttpServletRequest request, TaskProject taskProject) {
		String result = null;
		SysUser sysUser = getSessionSysUser(request);
		try {
			if(taskProject.getId() == null) {
				taskProject.setAdduser(sysUser.getId());
				taskProjectService.save(taskProject);
			} else {
				taskProjectService.update(taskProject);
			}
			result = SUCCESS;
		} catch (Exception e) {
			logger.error("保存异常: " + e.getMessage(), e);
			result = ERROR;
		}
		ModelMap modelMap = new ModelMap();
		modelMap.put(RESULT, result);
		return modelMap;
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping(value = "/taskProject/f_json/delete")
	@ResponseBody
	public ModelMap delete(HttpServletRequest request, Integer id) {
		String result = null;
		try {
			taskProjectService.delete(id);
			result = SUCCESS;
		} catch (Exception e) {
			logger.error("删除异常: " + e.getMessage(), e);
			result = ERROR;
		}
		ModelMap modelMap = new ModelMap();
		modelMap.put(RESULT, result);
		return modelMap;
	}
}
