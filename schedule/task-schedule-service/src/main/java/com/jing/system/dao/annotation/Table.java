package com.jing.system.dao.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Table {

	/**
	 * 表名
	 * @return
	 */
	public String name() default "";
}
