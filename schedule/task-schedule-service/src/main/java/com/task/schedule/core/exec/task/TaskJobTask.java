package com.task.schedule.core.exec.task;

import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jing.system.utils.DateUtil;
import com.jing.system.utils.FrameHttpUtil;
import com.jing.system.utils.FrameSpringBeanUtil;
import com.jing.system.utils.FrameStringUtil;
import com.jing.system.utils.JsonUtil;
import com.jing.system.utils.MapUtil;
import com.task.schedule.comm.enums.Boolean;
import com.task.schedule.comm.enums.Config;
import com.task.schedule.comm.enums.JobLogStatus;
import com.task.schedule.comm.utils.RuleVerifyUtil;
import com.task.schedule.comm.utils.SendMailUtil;
import com.task.schedule.comm.utils.SignUtil;
import com.task.schedule.core.base.AbstractTask;
import com.task.schedule.manager.pojo.TaskJob;
import com.task.schedule.manager.pojo.TaskJobLog;
import com.task.schedule.manager.pojo.TaskProject;
import com.task.schedule.manager.service.SysConfigService;
import com.task.schedule.manager.service.TaskJobLogService;
import com.task.schedule.manager.service.TaskJobService;
import com.task.schedule.manager.service.TaskProjectService;

/**
 * 系统公用的定时任务类
 * @author yuejing
 * @date 2015年3月29日 下午10:05:34
 * @version V1.0.0
 */
@Component
public class TaskJobTask extends AbstractTask {

	private static final Logger LOGGER = Logger.getLogger(TaskJobTask.class);
	@Autowired
	private TaskJobService taskJobService;
	@Autowired
	private TaskProjectService taskProjectService;
	@Autowired
	private TaskJobLogService taskJobLogService;

	@Override
	public void execute(JobExecutionContext context) {
		//TaskJobService taskJobService = SpringUtil.getBean(TaskJobService.class);
		final String time = DateUtil.getStrTime();
		String id = (String) context.getJobDetail().getJobDataMap().get("taskId");
		final TaskJob taskJob = taskJobService.get(Integer.valueOf(id));
		final TaskProject taskProject = taskProjectService.get(taskJob.getProjectid());
		new Thread(new Runnable() {
			@Override
			public void run() {
				//增加参数校验规则
				//String link = SignUtil.sign(taskJob.getLink(), taskProject);
				String link = taskJob.getLink();
				StringBuffer postParams = new StringBuffer();
				Map<String, String> params = SignUtil.signParams(taskProject);
				String content = FrameHttpUtil.post(link, params);
				
				
				/*TaskHttpUtil taskHttpUtil = new TaskHttpUtil(link);
				Map<String, String> params = SignUtil.signParams(taskProject);//JsonUtil.toMap(taskProject.getSignstring());
				Iterator<Entry<String, String>> entryKeyIterator = params.entrySet().iterator();
				while (entryKeyIterator.hasNext()) {
					Entry<String, String> e = entryKeyIterator.next();
					taskHttpUtil.addParam(e.getKey(), e.getValue());
					postParams.append(e.getKey()).append(":").append(e.getValue()).append(",");
				}
				String content = taskHttpUtil.post();*/
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
							StringBuffer mailContent = new StringBuffer();
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
								Map<String, Object> map = JsonUtil.toMap(content);
								String isSendMail = MapUtil.getString(map, "isSendMail");
								if("true".equals(isSendMail)) {
									String mailTitle = MapUtil.getString(map, "mailTitle");
									String mailContent = MapUtil.getString(map, "mailContent");

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
				
				link = link + "<br/>请求参数：" + postParams.toString();
				taskJobLogService.save(new TaskJobLog(taskJob.getId(), DateUtil.stringToDate(time), status, link, content));
			}
		}).start();
	}
}