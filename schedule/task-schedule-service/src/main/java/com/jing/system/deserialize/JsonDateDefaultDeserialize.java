package com.jing.system.deserialize;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import com.jing.system.utils.DateUtil;

/**
 * 日期格式以yyyy-MM-dd HH:mm:ss这种形式展示
 * 		在对象的属性的set方法上使用：@JsonDeserialize(using = JsonDateDefaultDeserialize.class)
 * @author yuejing
 * @email  yuejing0129@163.com 
 * @date   2014年10月23日 下午2:31:24 
 * @version 1.0.0
 */
public class JsonDateDefaultDeserialize extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return DateUtil.stringToDate(jp.getText());
	}
}