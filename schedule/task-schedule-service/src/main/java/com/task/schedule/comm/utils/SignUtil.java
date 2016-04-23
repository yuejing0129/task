package com.task.schedule.comm.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.jing.system.utils.JsonUtil;
import com.jing.system.utils.MD5Util;
import com.jing.system.utils.StringUtil;
import com.task.schedule.comm.enums.ProjectSign;
import com.task.schedule.manager.pojo.TaskProject;

/**
 * 签名工具类
 * @author  yuejing
 * @email   yuejing0129@163.com 
 * @net		www.suyunyou.com
 * @date    2015年3月31日 上午11:11:21 
 * @version 1.0.0
 */
public class SignUtil {
	
	public static Map<String, String> signParams(TaskProject project) {
		Map<String, String> params = JsonUtil.toMap(project.getSignstring());
		String signString = "";
		String signParam = null;
		Iterator<Entry<String, String>> entryKeyIterator = params.entrySet().iterator();
		while (entryKeyIterator.hasNext()) {
			Entry<String, String> e = entryKeyIterator.next();
			String value = e.getValue();
			if("theCurrentTimestamp".equals(value)) {
				value = String.valueOf(System.currentTimeMillis());
				params.put(e.getKey(), value);
			} else if("encryptionParameters".equals(value)) {
				signParam = e.getKey();
				continue;
			}
			signString += value;
		}
		if(StringUtil.isNotEmpty(signParam)) {
			params.put(signParam, MD5Util.getInstance().getEncString(signString).toLowerCase());
		}
		params.remove("token");
		return params;
	}

	public static String sign(String link, TaskProject project) {
		if(StringUtil.isEmpty(project.getSignstring())
				|| ProjectSign.NORMAL.getCode().equals(project.getSign())) {
			return link;
		}
		link += (link.indexOf("?") == -1 ? "?":"&");
		@SuppressWarnings("unchecked")
		Map<String, String> params = JsonUtil.toMap(project.getSignstring());
		String signString = "";
		String signParam = null;
		Iterator<Entry<String, String>> entryKeyIterator = params.entrySet().iterator();
		while (entryKeyIterator.hasNext()) {
			Entry<String, String> e = entryKeyIterator.next();
			String value = e.getValue();
			if("theCurrentTimestamp".equals(value)) {
				value = String.valueOf(System.currentTimeMillis());
			} else if("encryptionParameters".equals(value)) {
				signParam = e.getKey();
				continue;
			}
			signString += value;
			if(!"token".equals(e.getKey())) {
				//token不做为请求参数提交
				link += e.getKey() + "=" + value + "&";
			}
		}
		if(StringUtil.isNotEmpty(signParam)) {
			link += signParam + "=";
		}
		link += MD5Util.getInstance().getEncString(signString).toLowerCase();
		/*if(ProjectSign.MD5_CTT.getCode().equals(project.getSign())) {
			//md5(渠道+时间戳+token)
			link += MD5Util.getInstance().getEncString(signString);
		}*/
		return link;
	}
}
