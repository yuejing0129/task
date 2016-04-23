package com.jing.system.serializer;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.jing.system.utils.DateUtil;

/**
 * 日期格式以yyyy-MM-dd这种形式展示
 * @author  yuejing
 * @email   yuejing0129@163.com 
 * @date    2014年10月23日 下午2:32:57 
 * @version 1.0.0
 */
public class JsonDateYMdSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		jgen.writeString(DateUtil.dateToString(value, DateUtil.FMT_YYYY_MM_DD));
	}
}