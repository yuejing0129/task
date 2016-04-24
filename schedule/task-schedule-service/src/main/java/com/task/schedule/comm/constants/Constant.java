package com.task.schedule.comm.constants;

import java.util.UUID;


/**
 * 常量信息
 * @author yuejing
 * @date 2013-8-16 下午9:20:46
 * @version V1.0.0
 */
public class Constant {

	public static final String UTF_8 = "utf-8";
	public static final String POST = "post";
	public static final String GET = "get";
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	
	public static String webroot = null;
	
	/** 上传图片的最大值 */
	public static final Integer UPLOAD_FILE_IMG_MAX = 1024 * 2;
	/** 上传文件的最大值 */
	public static final Integer UPLOAD_FILE_MAX = 1024 * 5;
	
	/** 用户的Session Key */
	public final static String SESSION_SYS_USER_KEY = "user";
	
	/** 主线程的ID */
	public static final String TASK_ID_MAIN = "main";
	/** 清除任务日志的ID */
	public static final String TASK_ID_TASKJOBLOG_CLEAN = "taskJobLogClean";

	/** 服务的唯一编码 */
	private static String serviceCode = null;
	/**
	 * 获取当前服务的code
	 * @return
	 */
	public static String serviceCode() {
		if(serviceCode == null) {
			serviceCode = UUID.randomUUID().toString();
		}
        return serviceCode; 
	}
}