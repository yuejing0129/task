package com.task.schedule.manager.pojo;

import java.util.Date;
import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.jing.system.dao.annotation.Column;
import com.jing.system.dao.annotation.Table;
import com.jing.system.model.BaseEntity;

/**
 * sys_user实体
 * @author yuejing
 * @date 2015-03-30 14:07:27
 * @version V1.0.0
 */
@Table(name="sys_user")
@SuppressWarnings("serial")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class SysUser extends BaseEntity implements Serializable {
	//编号
	@Column(name="id", isAuto=true, isPk=true)
	private Integer id;
	//用户名
	@Column(name="username")
	private String username;
	//密码
	@Column(name="password")
	private String password;
	//昵称
	@Column(name="nickname")
	private String nickname;
	//添加时间
	@Column(name="addtime")
	private Date addtime;
	//添加人
	@Column(name="adduser")
	private Integer adduser;
	//状态【0正常、1冻结】GeneralStatus
	@Column(name="status")
	private Integer status;
	
	//========================== 扩展字段
	//状态名称
	private String statusname;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
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
	public String getStatusname() {
		return statusname;
	}
	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}
}