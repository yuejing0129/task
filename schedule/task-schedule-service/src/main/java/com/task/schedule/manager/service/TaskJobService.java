package com.task.schedule.manager.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jing.system.model.MyPage;
import com.jing.system.threadpool.FrameThreadAction;
import com.jing.system.threadpool.FrameThreadPool;
import com.jing.system.threadpool.FrameThreadPoolUtil;
import com.jing.system.utils.FrameHttpUtil;
import com.jing.system.utils.FrameJsonUtil;
import com.jing.system.utils.FrameMapUtil;
import com.jing.system.utils.FrameSpringBeanUtil;
import com.jing.system.utils.FrameStringUtil;
import com.jing.system.utils.FrameTimeUtil;
import com.task.schedule.comm.enums.Boolean;
import com.task.schedule.comm.enums.Config;
import com.task.schedule.comm.enums.JobLogStatus;
import com.task.schedule.comm.enums.JobStatus;
import com.task.schedule.comm.utils.RuleVerifyUtil;
import com.task.schedule.comm.utils.SendMailUtil;
import com.task.schedule.comm.utils.SignUtil;
import com.task.schedule.core.exec.JobService;
import com.task.schedule.manager.dao.TaskJobDao;
import com.task.schedule.manager.pojo.TaskJob;
import com.task.schedule.manager.pojo.TaskJobLog;
import com.task.schedule.manager.pojo.TaskProject;

/**
 * task_job的Service
 * @author yuejing
 * @date 2015-03-30 14:07:27
 * @version V1.0.0
 */
@Component
public class TaskJobService {
	
	private static final Logger LOGGER = Logger.getLogger(TaskJobService.class);

	@Autowired
	private TaskJobDao taskJobDao;
	@Autowired
	private JobService jobService;
	@Autowired
	private SysConfigService configService;
	@Autowired
	private TaskProjectService taskProjectService;
	@Autowired
	private TaskJobLogService taskJobLogService;
	
	/**
	 * 保存
	 * @param taskJob
	 */
	public void save(TaskJob taskJob) {
		if(taskJob.getStatus().intValue() != JobStatus.STOP.getCode().intValue()) {
			taskJob.setStatus(JobStatus.WAIT.getCode());
		}
		if(taskJob.getIsfailmail() == null) {
			taskJob.setIsfailmail(Boolean.FALSE.getCode());
		}
		taskJobDao.save(taskJob);
	}

	/**
	 * 删除
	 * @param id
	 * @throws SchedulerException 
	 */
	public void delete(Integer id) throws SchedulerException {
		jobService.deleteJob(id.toString());
		taskJobDao.delete(id);
	}
	
	public void deleteProjectid(Integer projectid) throws SchedulerException {
		List<TaskJob> list = findByProjectid(projectid);
		for (TaskJob taskJob : list) {
			delete(taskJob.getId());
		}
	}

	private List<TaskJob> findByProjectid(Integer projectid) {
		return taskJobDao.findByProjectid(projectid);
	}

	/**
	 * 修改
	 * @param taskJob
	 * @throws SchedulerException 
	 */
	public void update(TaskJob taskJob) throws SchedulerException {
		if(taskJob.getStatus().intValue() != JobStatus.STOP.getCode().intValue()) {
			//状态不为停止，则改为待添加
			taskJob.setStatus(JobStatus.WAIT.getCode());
		}
		//设置让该任务不绑定服务，为待添加则重新分配
		taskJob.setServid(null);
		taskJobDao.update(taskJob);
	}

	/**
	 * 修改任务状态
	 * @param id
	 * @param status
	 */
	public void updateStatus(Integer id, Integer status) {
		taskJobDao.updateStatus(id, status);
	}

	/**
	 * 根据id获取对象
	 * @param id
	 * @return
	 */
	public TaskJob get(Integer id) {
		return taskJobDao.get(id);
	}

	/**
	 * 分页获取对象
	 * @param taskJob
	 * @return
	 */
	public MyPage<TaskJob> pageQuery(TaskJob taskJob) {
		taskJob.setDefPageSize();
		int total = taskJobDao.findTaskJobCount(taskJob);
		List<TaskJob> rows = null;
		if(total > 0) {
			rows = taskJobDao.findTaskJob(taskJob);
			for (TaskJob job : rows) {
				job.setStatusname(JobStatus.getText(job.getStatus()));
				job.setIsfailmailname(Boolean.getText(job.getIsfailmail()));
			}
		}
		MyPage<TaskJob> page = new MyPage<TaskJob>(taskJob.getPage(), taskJob.getSize(), total, rows);
		return page;
	}

	/**
	 * 将任务执行过期30s和状态不为停止的 改为加入待执行
	 */
	public void updateWait() {
		Integer destroyTime = Integer.valueOf(configService.getValue(Config.LOCK_DESTROY_TIME, "30"));
		taskJobDao.updateWait(JobStatus.WAIT.getCode(), JobStatus.STOP.getCode(), destroyTime);
	}

	/**
	 * 根据状态获取任务列表
	 * @param status
	 * @param topnum
	 * @return
	 */
	public List<TaskJob> findByStatus(Integer status, Integer topnum) {
		return taskJobDao.findByStatus(status, topnum);
	}

	/**
	 * 获取非停止的任务
	 * @param status
	 * @param topnum
	 * @return
	 */
	public List<TaskJob> findActive() {
		return taskJobDao.findActive(JobStatus.NORMAL.getCode(), JobStatus.WAIT.getCode());
	}

	/**
	 * 修改该服务待添加的任务
	 * @param servid
	 * @param topnum
	 */
	public void updateServidByWait(String servid, Integer topnum) {
		taskJobDao.updateByServid(JobStatus.WAIT.getCode(), servid, topnum);
	}

	/**
	 * 获取服务待执行的任务
	 * @param servid
	 * @param status
	 * @return
	 */
	public List<TaskJob> findByServidStatus(String servid, Integer status) {
		return taskJobDao.findByServidStatus(servid, status);
	}

	/**
	 * 根据服务和状态修改updatetime为当前时间
	 * @param servid
	 * @param status
	 */
	public void updateUpdatetimeByServidStatus(String servid, Integer status) {
		taskJobDao.updateUpdatetimeByServidStatus(servid, status);
	}

	/**
	 * 释放job为没有服务(servid会修改为null)，状态为待添加
	 * @param id
	 */
	public void updateRelease(Integer id) {
		taskJobDao.updateRelease(id, JobStatus.WAIT.getCode());
	}

	/**
	 * 获取正常执行的任务数目
	 * @param servid
	 * @return
	 */
	public List<Map<String, Object>> findServidCount() {
		return taskJobDao.findServidCountByStatus(JobStatus.NORMAL.getCode());
	}
	
	/**
	 * 获取项目状态的任务数目
	 * @return
	 */
	public List<Map<String, Object>> findProjectidCount() {
		return taskJobDao.findProjectidCount();
	}

	/**
	 * 执行job任务
	 * @param id
	 */
	public void execJob(Integer id) {
		final TaskJob taskJob = get(id);
		
		final String time = FrameTimeUtil.getStrTime();
		final TaskProject taskProject = taskProjectService.get(taskJob.getProjectid());

		//获取做任务的线程池
		FrameThreadPool pool = FrameThreadPoolUtil.getThreadPool("task_job", 30);
		pool.invoke(new FrameThreadAction() {
			private static final long serialVersionUID = -4514045785524480607L;
			@Override
			protected void compute() {
				//增加参数校验规则
				String link = taskJob.getLink();
				StringBuilder postParams = new StringBuilder();
				Map<String, String> params = SignUtil.signParams(taskProject);
				Iterator<Entry<String, String>> entryKeyIterator = params.entrySet().iterator();
				while (entryKeyIterator.hasNext()) {
					Entry<String, String> e = entryKeyIterator.next();
					postParams.append("&").append(e.getKey()).append("=").append(e.getValue());
				}
				if(postParams.length() > 0) {
					postParams.setCharAt(postParams.length() - 1, ' ');
				}
				String content = FrameHttpUtil.post(link, params);
				if(LOGGER.isInfoEnabled()) {
					LOGGER.info("\n" + time + "-调用任务 ID【" + taskJob.getId() + "】名称【" + taskJob.getName() + "】\n请求地址: " + link);
				}
				//根据响应的内容做相应的记录
				Integer status = JobLogStatus.NORMAL.getCode();
				String toMails = taskProject.getRecemail();
				if(FrameStringUtil.isEmpty(content)) {
					status = JobLogStatus.ERROR.getCode();
					content = JobLogStatus.ERROR.getName();

					//处理是否需要发邮件
					if(Boolean.TRUE.getCode().intValue() == taskProject.getIsrecemail().intValue()
							&& Boolean.TRUE.getCode().intValue() == taskJob.getIsfailmail().intValue()) {
						if(LOGGER.isInfoEnabled()) {
							LOGGER.info("调用任务【" + taskJob.getName() + "】请求失败，发送邮件");
						}
						if(FrameStringUtil.isNotEmpty(toMails)) {
							//发送失败邮件
							StringBuffer title = new StringBuffer();
							title.append(time).append("-调用任务【").append(taskProject.getName()).append("-").append(taskJob.getName()).append("】失败!!!");
							StringBuilder mailContent = new StringBuilder();
							mailContent.append("项目名称：").append(taskProject.getName()).append("<br/>");
							mailContent.append("任务名称：").append(taskJob.getName()).append("<br/>");
							mailContent.append("任务描述：").append(taskJob.getRemark()).append("<br/>");
							mailContent.append("调用时间：").append(time).append("<br/>");
							mailContent.append("调用地址：").append(link).append("<br/>");
							mailContent.append("请求参数：").append(postParams.toString()).append("<br/>");
							mailContent.append("错误原因：可能是接口地址不通，或网络不通");

							SysConfigService configService = FrameSpringBeanUtil.getBean(SysConfigService.class);
							SendMailUtil.sendMail(configService.getValue(Config.MAIL_SMTP), configService.getValue(Config.MAIL_FROM), configService.getValue(Config.MAIL_USERNAME), configService.getValue(Config.MAIL_PASSWORD),
									toMails, title.toString(), mailContent.toString());
						}
					}
				} else {
					//调度成功
					if(FrameStringUtil.isNotEmpty(content)) {
						if(RuleVerifyUtil.isHttpResultSendMail(content)) {
							//根据返回结果发邮件
							try {
								Map<String, Object> map = FrameJsonUtil.toMap(content);
								String isSendMail = FrameMapUtil.getString(map, "isSendMail");
								if("true".equals(isSendMail)) {
									String mailTitle = FrameMapUtil.getString(map, "mailTitle");
									String mailContent = FrameMapUtil.getString(map, "mailContent");

									SysConfigService configService = FrameSpringBeanUtil.getBean(SysConfigService.class);
									SendMailUtil.sendMail(configService.getValue(Config.MAIL_SMTP), configService.getValue(Config.MAIL_FROM), configService.getValue(Config.MAIL_USERNAME), configService.getValue(Config.MAIL_PASSWORD),
											toMails, mailTitle, mailContent);
								}
							} catch (Exception e) {
								//不需要发邮件
								LOGGER.error("配置发送邮件的格式错误!" + e.getMessage());
							}
						}
					}
				}

				link = link + " | params=" + postParams.toString();
				taskJobLogService.save(new TaskJobLog(taskJob.getId(), FrameTimeUtil.parseDate(time), status, link, content));
			}
		});
	}
}