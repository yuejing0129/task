package com.task.schedule.manager.pojo;

import java.util.Date;
import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.jing.system.dao.annotation.Column;
import com.jing.system.dao.annotation.Table;
import com.jing.system.model.BaseEntity;

/**
 * task_project实体
 * @author yuejing
 * @date 2015-03-30 14:07:28
 * @version V1.0.0
 */
@Table(name="task_project")
@SuppressWarnings("serial")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class TaskProject extends BaseEntity implements Serializable {
	//编号
	@Column(name="id", isAuto=true, isPk=true)
	private Integer id;
	//名称
	@Column(name="name")
	private String name;
	//描述
	@Column(name="remark")
	private String remark;
	//接收邮件（0否1是）
	@Column(name="isrecemail")
	private Integer isrecemail;
	//接收邮箱（多个,分隔）
	@Column(name="recemail")
	private String recemail;
	//添加时间
	@Column(name="addtime")
	private Date addtime;
	//添加人
	@Column(name="adduser")
	private Integer adduser;
	//签名规则
	@Column(name="sign")
	private String sign;
	//签名参数与内容
	@Column(name="signstring")
	private String signstring;
	
	//========================= 扩展属性
	//是否发邮件名称
	private String isrecemailname;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getSignstring() {
		return signstring;
	}
	public void setSignstring(String signstring) {
		this.signstring = signstring;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public Integer getIsrecemail() {
		return isrecemail;
	}
	public void setIsrecemail(Integer isrecemail) {
		this.isrecemail = isrecemail;
	}
	public String getRecemail() {
		return recemail;
	}
	public void setRecemail(String recemail) {
		this.recemail = recemail;
	}
	public String getIsrecemailname() {
		return isrecemailname;
	}
	public void setIsrecemailname(String isrecemailname) {
		this.isrecemailname = isrecemailname;
	}
}