package com.mavin.dingtalk.annotation;

import java.lang.annotation.*;

/**
 * @author Mavin
 * @date 2024/6/21 16:57
 * @description 方法描述注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface MethodDesc {
    String value();
}
