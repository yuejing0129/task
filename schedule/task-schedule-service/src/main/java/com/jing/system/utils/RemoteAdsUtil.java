package com.jing.system.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * RemoteAddress的工具类
 * @author jing.yue
 * @version 1.0
 * @since 2013/1/3
 */
public class RemoteAdsUtil {

	/**
	 * 获取客户端IP地址
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String getRemoteAddress(HttpServletRequest request) throws Exception {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
