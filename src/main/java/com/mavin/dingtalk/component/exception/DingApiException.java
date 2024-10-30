package com.mavin.dingtalk.component.exception;

/**
 * @author Mavin
 * @date 2024/6/24 14:58
 * @description 统一异常处理
 */
public class DingApiException extends RuntimeException {

    public DingApiException(String message) {
        super(message);
    }

    public DingApiException(Throwable cause) {
        super(cause);
    }

    public DingApiException(String message, Throwable cause) {
        super(message, cause);
    }

}
