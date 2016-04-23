package com.task.schedule.manager.pojo;

import java.util.Date;
import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.jing.system.dao.annotation.Column;
import com.jing.system.dao.annotation.Table;
import com.jing.system.model.BaseEntity;

/**
 * task_job实体
 * @author yuejing
 * @date 2015-03-30 14:07:27
 * @version V1.0.0
 */
@Table(name="task_job")
@SuppressWarnings("serial")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class TaskJob extends BaseEntity implements Serializable {
	//编号
	@Column(name="id", isAuto=true, isPk=true)
	private Integer id;
	//项目编号
	@Column(name="projectid")
	private Integer projectid;
	//名称
	@Column(name="name")
	private String name;
	//描叙
	@Column(name="remark")
	private String remark;
	//调用链接
	@Column(name="link")
	private String link;
	//执行规则
	@Column(name="cron")
	private String cron;
	//失败发邮件（0否1是）
	@Column(name="isfailmail")
	private Integer isfailmail;
	//添加时间
	@Column(name="addtime")
	private Date addtime;
	//添加人
	@Column(name="adduser")
	private Integer adduser;
	//状态【0正常、1停止、2异常终止】
	@Column(name="status")
	private Integer status;
	//状态消息
	@Column(name="statusmsg")
	private String statusmsg;
	
	//====================== 扩展属性
	//状态名称
	private String statusname;
	//失败是否发邮件名称
	private String isfailmailname;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProjectid() {
		return projectid;
	}
	public void setProjectid(Integer projectid) {
		this.projectid = projectid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getCron() {
		return cron;
	}
	public void setCron(String cron) {
		this.cron = cron;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public Integer getAdduser() {
		return adduser;
	}
	public void setAdduser(Integer adduser) {
		this.adduser = adduser;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStatusmsg() {
		return statusmsg;
	}
	public void setStatusmsg(String statusmsg) {
		this.statusmsg = statusmsg;
	}
	public String getStatusname() {
		return statusname;
	}
	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}
	public Integer getIsfailmail() {
		return isfailmail;
	}
	public void setIsfailmail(Integer isfailmail) {
		this.isfailmail = isfailmail;
	}
	public String getIsfailmailname() {
		return isfailmailname;
	}
	public void setIsfailmailname(String isfailmailname) {
		this.isfailmailname = isfailmailname;
	}
}