package com.task.schedule.comm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.task.schedule.comm.constants.Constant;
import com.task.schedule.manager.pojo.SysUser;

/**
 * 用户Session拦截器
 * @author yuejing
 * @date 2013-8-16 下午10:09:43
 * @version V1.0.0
 */
public class AdminSecurityInterceptor implements HandlerInterceptor {

	//登录的URL
	private String indexUrl;

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	/**
	 * 在Controller方法前进行拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		SysUser user = (SysUser) request.getSession().getAttribute(Constant.SESSION_SYS_USER_KEY);
		if(user == null) {
			//TODO 模拟登录
			/*SysUserService sysUserService = SpringUtil.getBean(SysUserService.class);
			SysUser sysUser = new SysUser();
			sysUser.setUsername("admin");
			sysUser.setPassword("admin");
			Result<SysUser> result = sysUserService.login(sysUser);
			if(Constant.SUCCESS.equals(result.getResult())) {
				request.getSession().setAttribute(Constant.SESSION_SYS_USER_KEY, result.getData());
				return true;
			}*/
			
			response.sendRedirect(request.getContextPath() + indexUrl);
			return false;
		}
		return true;
	}

	public String getIndexUrl() {
		return indexUrl;
	}
	public void setIndexUrl(String indexUrl) {
		this.indexUrl = indexUrl;
	}
}