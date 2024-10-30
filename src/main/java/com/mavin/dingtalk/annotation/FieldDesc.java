package com.mavin.dingtalk.annotation;

import java.lang.annotation.*;

/**
 * @author Mavin
 * @date 2024/6/21 16:56
 * @description 字段描述注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface FieldDesc {
    String value();
}
