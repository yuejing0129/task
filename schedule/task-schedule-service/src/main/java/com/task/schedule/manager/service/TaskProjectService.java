package com.task.schedule.manager.service;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.task.schedule.comm.enums.Boolean;
import com.task.schedule.manager.dao.TaskProjectDao;
import com.task.schedule.manager.pojo.TaskProject;
import com.jing.system.model.MyPage;
import com.jing.system.utils.DateUtil;

/**
 * task_project的Service
 * @author yuejing
 * @date 2015-03-30 14:07:28
 * @version V1.0.0
 */
@Component
public class TaskProjectService {

	@Autowired
	private TaskProjectDao taskProjectDao;
	@Autowired
	private TaskJobService taskJobService;
	
	/**
	 * 保存
	 * @param taskProject
	 */
	public void save(TaskProject taskProject) {
		taskProject.setAddtime(DateUtil.getTime());
		if(taskProject.getIsrecemail() == null) {
			taskProject.setIsrecemail(Boolean.FALSE.getCode());
		}
		taskProjectDao.save(taskProject);
	}

	/**
	 * 删除
	 * @param id
	 * @throws SchedulerException 
	 */
	public void delete(Integer id) throws SchedulerException {
		taskProjectDao.delete(id);
		//根据项目ID删除任务
		taskJobService.deleteProjectid(id);
	}

	/**
	 * 修改
	 * @param taskProject
	 */
	public void update(TaskProject taskProject) {
		taskProjectDao.update(taskProject);
	}

	/**
	 * 根据id获取对象
	 * @param id
	 * @return
	 */
	public TaskProject get(Integer id) {
		return taskProjectDao.get(id);
	}

	/**
	 * 分页获取对象
	 * @param taskProject
	 * @return
	 */
	public MyPage<TaskProject> pageQuery(TaskProject taskProject) {
		MyPage<TaskProject> page = taskProjectDao.pageQuery(taskProject);
		for (TaskProject project : page.getRows()) {
			project.setIsrecemailname(Boolean.getText(project.getIsrecemail()));
		}
		return page;
	}
}
