package com.jing.system.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

/**
 * Date Operate Class
 * @author jing.yue
 * @version 1.0
 * @since 2012-03-30
 *
 */
public class DateUtil {
	/** 默认的格式: yyyy-MM-dd HH:mm:ss */
	public static String FMT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	/** 格式: yyyyMMddHHmmss */
	public static String FMT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	/** 格式: yyyyMMddHH */
	public static String FMT_YYYYMMDDHH = "yyyyMMddHH";
	/** 格式: yyyyMMdd */
	public static String FMT_YYYYMMDD = "yyyyMMdd";
	/** 格式: yyyy */
	public static String FMT_YYYY = "yyyy";
	/** 格式: yyyy-MM-dd */
	public static String FMT_YYYY_MM_DD = "yyyy-MM-dd";
	/** 格式: yyyy-MM */
	public static String FMT_YYYY_MM = "yyyy-MM";
	/** 格式: yyyy-MM-dd HH */
	public static final String FMT_YYYY_MM_DD_HH = "yyyy-MM-dd HH";
	/** 格式: yyyy-MM-dd HH:mm */
	public static final String FMT_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

	/**
	 * 日期格式转为字符串格式[格式：yyyy-MM-dd HH:mm:ss]
	 * @param date：传入的日期
	 * @return    ：String
	 */
	public static String dateToString(Date date){
		SimpleDateFormat simpDateFormat = new SimpleDateFormat(FMT_DEFAULT);
	    return simpDateFormat.format(date);
	}

	/**
	 * 日期格式转为字符串格式
	 * @param date：传入的日期
	 * @param fmt ：转为字符的格式
	 * @return    ：String
	 */
	public static String dateToString(Date date, String fmt){
		if(date == null) {
			return null;
		}
		SimpleDateFormat simpDateFormat = new SimpleDateFormat(fmt);
	    return simpDateFormat.format(date);
	}

	/**
	 * 字符串格式转日期
	 * @param str：传入的字符串
	 * @return   ：Date
	 */
	public static Date stringToDate(String str){
		SimpleDateFormat simpDateFormat = new SimpleDateFormat(FMT_DEFAULT);
	    try {
			return simpDateFormat.parse(str);
		} catch (ParseException e) {
			throw new RuntimeException("日期格式不正确 value[" + str + "]");
		}
	}

	/**
	 * 字符串格式转日期
	 * @param str：传入的字符串
	 * @param fmt：转为的日期格式
	 * @return   ：Date
	 */
	public static Date stringToDate(String str, String fmt){
		SimpleDateFormat simpDateFormat = new SimpleDateFormat(fmt);
	    try {
			return simpDateFormat.parse(str);
		} catch (ParseException e) {
			throw new RuntimeException("日期格式不正确 value[" + str + "] fmt[" + fmt + "]");
		}
	}

	/**
	 * 获取当前字符串日期格式[yyyy-MM-dd]
	 * @return
	 */
	public static String getTodayYyyyMmDd() {
		return DateUtil.dateToString(DateUtil.getTime(), DateUtil.FMT_YYYY_MM_DD);
	}

	/**
	 * 获取当前字符串日期格式[yyyy]
	 * @return
	 */
	public static String getTodayYyyy() {
		return DateUtil.dateToString(DateUtil.getTime(), DateUtil.FMT_YYYY);
	}

	/**
	 * 获取当前时间[yyyy-MM-dd HH:mm:ss]
	 * @return
	 */
	public static String getStrTime() {
		return dateToString(getTime(), DateUtil.FMT_DEFAULT);
	}

	/**
	 * 获取当前时间
	 * @return
	 */
	public static Date getTime() {
		return new Date();
	}

	/**
	 * 在指定日期上添加指定的年
	 * @param date		：传入的日期
	 * @param amount 	：添加的年
	 * @return
	 */
	public static Date addYears(Date date, Integer amount){
		return DateUtils.addYears(date, amount);
	}

	/**
	 * 在指定日期上添加指定的月份
	 * @param date		：传入的日期
	 * @param amount 	：添加的月份
	 * @return
	 */
	public static Date addMonths(Date date, Integer amount){
		return DateUtils.addMonths(date, amount);
	}
	
	/**
	 * 在指定日期上添加指定的天数
	 * @param date		：传入的日期
	 * @param amount 	：添加的天数
	 * @return
	 */
	public static Date addDays(Date date, Integer amount){
		return DateUtils.addDays(date, amount);
	}

	/**
	 * 在指定日期上添加指定的小时
	 * @param date		：传入的日期
	 * @param amount 	：添加的小时
	 * @return
	 */
	public static Date addHours(Date date, Integer amount) {
		return DateUtils.addHours(date, amount);
	}

	/**
	 * 在指定日期上添加指定的分钟
	 * @param date		：传入的日期
	 * @param amount 	：添加的分钟
	 * @return
	 */
	public static Date addMinutes(Date date, Integer amount) {
		return DateUtils.addMinutes(date, amount);
	}

	/**
	 * 在指定日期上添加指定的秒数
	 * @param date		：传入的日期
	 * @param amount 	：添加的秒数
	 * @return
	 */
	public static Date addSeconds(Date date, Integer amount){
		return DateUtils.addSeconds(date, amount);
	}

	/**
	 * 获取时间差
	 * @param beginTime	开始时间
	 * @param endTime	结束时间
	 * @param type		时间的类型[0秒/1分钟/2小时]
	 * @return
	 */
	public static Long getDateDiff(Date beginTime, Date endTime, Integer type) {
		long time = 0;
		long diffTime = endTime.getTime() - beginTime.getTime();
		switch (type) {
		case 0:
			time = diffTime/1000;
			break;
		case 1:
			time = diffTime/(60*1000);
			break;
		case 2:
			time = diffTime/(60*60*1000);
			break;
		}
		return time;
	}
	
	/**
	 * 获取简单时间[比如30秒前 20分钟前等]
	 * @param timeStr
	 * @return
	 */
	public static String getSimpleTime(String timeStr) {
		Date time = DateUtil.stringToDate(timeStr);
		return getSimpleTime(time);
	}
	/**
	 * 获取简单时间[比如30秒前 20分钟前等]
	 * @param time
	 * @return
	 */
	public static String getSimpleTime(Date time) {
		long ssDiff = DateUtil.getDateDiff(time, DateUtil.getTime(), 0);
		StringBuffer result = new StringBuffer();
		if(ssDiff < 60) {
			result.append(ssDiff).append("秒钟前");
			return result.toString();
		}
		long mmDiff = DateUtil.getDateDiff(time, DateUtil.getTime(), 1);
		if(mmDiff < 60) {
			result.append(mmDiff).append("分钟前");
			return result.toString();
		}
		String timeStr = DateUtil.dateToString(time);
		if(DateUtil.getTodayYyyyMmDd().equals(timeStr.substring(0, 10))) {
			result.append("今天 ").append(DateUtil.dateToString(time, "HH:mm"));
			return result.toString();
		}
		if(getTodayYyyy().equals(timeStr.substring(0, 4))) {
			return DateUtil.dateToString(time, "MM月dd日 HH:mm");
		}
		return DateUtil.dateToString(time, "yyyy年MM月dd日");
	}
}