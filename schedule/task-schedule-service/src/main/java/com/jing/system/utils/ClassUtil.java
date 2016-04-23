package com.jing.system.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * class工具类
 * @author jing.yue
 * @version 1.0
 * @since 2012-10-26
 */
public class ClassUtil {

	private static Map<String, String> firstUpperClsNameMap = new HashMap<String, String>();
	/**
	 * 获取class首字母小写的类名
	 * @param cls
	 * @return
	 */
	public static String getFirstLowerClsName(Class<?> cls) {
		String fUpperClsName = firstUpperClsNameMap.get(cls.getName());
		if(fUpperClsName == null) {
			StringBuffer sb = new StringBuffer(cls.getSimpleName());
			sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
			fUpperClsName = sb.toString();
			firstUpperClsNameMap.put(cls.getName(), fUpperClsName);
		}
		return fUpperClsName;
	}
	
	public static void main(String[] args) {
		System.out.println(ClassUtil.getFirstLowerClsName(ClassUtil.class));
	}
}