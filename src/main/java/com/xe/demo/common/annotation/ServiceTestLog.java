package com.xe.demo.common.annotation;

import java.lang.annotation.*;

/**
 * test日志拦截
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})  
@Retention(RetentionPolicy.RUNTIME)  
@Documented
public @interface ServiceTestLog {

	String value() default "";
	int type() default 1;
	
}
