package com.jing.system.utils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Map工具类
 * @author  yuejing
 * @email   yuejing0129@163.com 
 * @net		www.suyunyou.com
 * @date    2014年12月25日 上午10:42:27 
 * @version 1.0.0
 */
@SuppressWarnings("rawtypes")
public class MapUtil {

	public static String getString(Map map, String key) {
		Object value = map.get(key);
		if(value == null) {
			return null;
		}
		return value.toString();
	}
	
	public static Integer getInt(Map map, String key) {
		Object value = map.get(key);
		if(value == null) {
			return null;
		}
		return Integer.valueOf(value.toString());
	}
	
	public static Long getLong(Map map, String key) {
		Object value = map.get(key);
		if(value == null) {
			return null;
		}
		return Long.valueOf(value.toString());
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> getListString(Map map, String key) {
		Object value = map.get(key);
		if(value == null) {
			return null;
		}
		return (List<String>) value;
	}

	@SuppressWarnings("unchecked")
	public static List<Map> getListMap(Map map, String key) {
		Object value = map.get(key);
		if(value == null) {
			return null;
		}
		return (List<Map>) value;
	}
	
	public static Map getMap(Map map, String key) {
		Object value = map.get(key);
		if(value == null) {
			return null;
		}
		return (Map) value;
	}
	
	public static Date getDate(Map map, String key, String fmt) {
		Object value = map.get(key);
		if(value == null) {
			return null;
		}
		return DateUtil.stringToDate(value.toString(), fmt);
	}
}