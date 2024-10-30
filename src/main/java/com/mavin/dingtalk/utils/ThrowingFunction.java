package com.mavin.dingtalk.utils;

/**
 * @author Mavin
 * @date 2024/6/26 13:56
 * @description
 */
@FunctionalInterface
public interface ThrowingFunction<T, R, E extends Throwable> {
    R apply(T t) throws E;
}
