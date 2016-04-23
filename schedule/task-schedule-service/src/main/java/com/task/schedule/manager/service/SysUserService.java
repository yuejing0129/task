package com.task.schedule.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jing.system.model.MyPage;
import com.jing.system.model.Result;
import com.jing.system.utils.DateUtil;
import com.jing.system.utils.MD5Util;
import com.jing.system.utils.StringUtil;
import com.task.schedule.comm.constants.Constant;
import com.task.schedule.comm.enums.GeneralStatus;
import com.task.schedule.manager.dao.SysUserDao;
import com.task.schedule.manager.pojo.SysUser;

/**
 * sys_user的Service
 * @author yuejing
 * @date 2015-03-30 14:07:27
 * @version V1.0.0
 */
@Component
public class SysUserService {

	@Autowired
	private SysUserDao sysUserDao;

	/**
	 * 登录
	 * @param usrInfo
	 * @return
	 */
	public Result<SysUser> login(SysUser sysUser) {
		Result<SysUser> result = new Result<SysUser>();
		SysUser user = findByUsername(sysUser.getUsername());
		if(user == null) {
			//不存在用户
			/*result.setResult("error_not_user");
			result.setMsg("该账户尚未注册!");*/
			result.setResult("error_info");
			result.setMsg("请输入正确的用户和密码!");
			return result;
		}
		if(user.getStatus().intValue() == GeneralStatus.FREEZE.getCode().intValue()) {
			//帐号冻结
			result.setResult("error_user_freeze");
			result.setMsg("您的帐号被冻结, 快去联系客服吧!");
			return result;
		}
		if(!MD5Util.getInstance().getEncString(sysUser.getPassword(), user.getId().toString()).equals(user.getPassword())) {
			//密码不正确
			/*result.setResult("error_pwd");
			result.setMsg("密码输入错误!");*/
			result.setResult("error_info");
			result.setMsg("请输入正确的用户和密码!");
			return result;
		}
		//密码相同
		result.setData(user);
		result.setResult(Constant.SUCCESS);
		return result;
	}

	private SysUser findByUsername(String username) {
		return sysUserDao.findByUsername(username);
	}

	/**
	 * 保存
	 * @param sysUser
	 */
	public void save(SysUser sysUser) {
		sysUser.setAddtime(DateUtil.getTime());
		sysUserDao.save(sysUser);
		//更新密码
		String password = MD5Util.getInstance().getEncString(sysUser.getPassword(), sysUser.getId().toString());
		sysUserDao.updatePassword(sysUser.getId(), password);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void delete(Integer id) {
		sysUserDao.delete(id);
	}

	/**
	 * 修改
	 * @param sysUser
	 */
	public void update(SysUser sysUser) {
		if(StringUtil.isNotEmpty(sysUser.getPassword())) {
			String password = MD5Util.getInstance().getEncString(sysUser.getPassword(), sysUser.getId().toString());
			sysUser.setPassword(password);
		}
		sysUserDao.update(sysUser);
	}

	/**
	 * 根据id获取对象
	 * @param id
	 * @return
	 */
	public SysUser get(Integer id) {
		return sysUserDao.get(id);
	}

	/**
	 * 分页获取对象
	 * @param sysUser
	 * @return
	 */
	public MyPage<SysUser> pageQuery(SysUser sysUser) {
		MyPage<SysUser> page = sysUserDao.pageQuery(sysUser);
		for (SysUser user : page.getRows()) {
			user.setStatusname(GeneralStatus.getText(user.getStatus()));
		}
		return page;
	}

}