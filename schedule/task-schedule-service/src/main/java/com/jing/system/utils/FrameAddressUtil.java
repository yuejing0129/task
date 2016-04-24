package com.jing.system.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * RemoteAddress的工具类
 * @author jing.yue
 * @version 1.0
 * @since 2013/1/3
 */
public class FrameAddressUtil {

	private static final Logger LOGGER = Logger.getLogger(FrameAddressUtil.class);

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

	/**
	 * 获取本机的ip地址
	 * @return
	 */
	public static String getLocalIP(){
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			LOGGER.error(e.getMessage(), e);
		}

		byte[] ipAddr = addr.getAddress();
		String ipAddrStr = "";
		for (int i = 0; i < ipAddr.length; i++) {
			if (i > 0) {
				ipAddrStr += ".";
			}
			ipAddrStr += ipAddr[i] & 0xFF;
		}
		return ipAddrStr;
	}
}