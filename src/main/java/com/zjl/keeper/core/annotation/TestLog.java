package com.zjl.keeper.core.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author joey
 * @description 测试AOP日志注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface TestLog {
    String value() default "默认参数";
    // 数组类型
    String[] tags() default {"tag1", "tag2"};
}
