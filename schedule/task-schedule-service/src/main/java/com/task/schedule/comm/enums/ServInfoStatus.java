package com.task.schedule.comm.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jing.system.model.KvEntity;

/**
 * 服务的状态[10正常、20已销毁]
 * @author 岳静
 * @date 2016年4月25日 下午2:58:00 
 * @version V1.0
 */
public enum ServInfoStatus {
	NORMAL		(10, "正常"),
	DESTROY		(20, "已销毁");
	
	public static final String KEY = "serv_info_status";
	
	private Integer code;
	private String name;
	private static List<KvEntity> list = new ArrayList<KvEntity>();
	private static Map<Integer, String> map = new HashMap<Integer, String>();

	private ServInfoStatus(Integer code, String name) {
		this.code = code;
		this.name = name;
	}
	
	static {
		EnumSet<ServInfoStatus> set = EnumSet.allOf(ServInfoStatus.class);
		for(ServInfoStatus e : set){
			map.put(e.getCode(), e.getName());
			list.add(new KvEntity(e.getCode().toString(), e.getName()));
		}
	}

	/**
	 * 根据Code获取对应的汉字
	 * @param code
	 * @return
	 */
	public static String getText(Integer code) {
		return map.get(code);
	}
	
	/**
	 * 获取集合
	 * @return
	 */
	public static List<KvEntity> getList() {
		return list;
	}

	public Integer getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
}
