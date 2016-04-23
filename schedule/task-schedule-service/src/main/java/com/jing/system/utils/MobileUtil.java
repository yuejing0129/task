package com.jing.system.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class MobileUtil {

	private static final Logger logger = Logger.getLogger(MobileUtil.class);

	/** 手机浏览器 */
	public static final String[] MOBILE_SPECIFIC_SUBSTRING = {
		"iPad","iPhone","Android","MIDP","Opera Mobi",
		"Opera Mini","BlackBerry","HP iPAQ","IEMobile",
		"MSIEMobile","Windows Phone","HTC","LG",
		"MOT","Nokia","Symbian","Fennec",
		"Maemo","Tear","Midori","armv",
		"Windows CE","WindowsCE","Smartphone","240x320",
		"176x220","320x320","160x160","webOS",
		"Palm","Sagem","Samsung","SGH",
		"SonyEricsson","MMP","UCWEB"};
	/*public static final String[] MOBILE_SPECIFIC_SUBSTRING = {
		"iPad","iPhone","Android","MIDP","Opera Mobi",
		"Opera Mini","BlackBerry","HP iPAQ","IEMobile",
		"MSIEMobile","Windows Phone","HTC","LG",
		"MOT","Nokia","Symbian","Fennec",
		"Maemo","Tear","Midori","armv",
		"Windows CE","WindowsCE","Smartphone","240x320",
		"176x220","320x320","160x160","webOS",
		"Palm","Sagem","Samsung","SGH",
		"SIE","SonyEricsson","MMP","UCWEB"};*/

	/**
	 * 判断是否为手机浏览器
	 */
	public static boolean checkMobile(HttpServletRequest request) {
		String userAgent = request.getHeader("user-agent");
		logger.info("check Mobile userAgent: " + userAgent);
		for (String mobile : MOBILE_SPECIFIC_SUBSTRING){
			if (userAgent.contains(mobile)
					|| userAgent.contains(mobile.toUpperCase())
					|| userAgent.contains(mobile.toLowerCase())){
				logger.info("userAgent.equals(" + mobile + ")");
				return true;
			}
		}
		return false;
	}
}
