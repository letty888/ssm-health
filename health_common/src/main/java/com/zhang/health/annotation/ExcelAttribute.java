package com.zhang.health.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/26 20:46
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelAttribute {

    /**
     * 对应的列名称
     */
    String name() default "";

    /**
     * excel列的索引
     */
    int sort();

    /**
     * 字段类型对应的格式
     */
    String format() default "";
}