package com.mavin.dingtalk.service.callback.minih5;

import com.mavin.dingtalk.component.application.IDingMiniH5;
import com.mavin.dingtalk.pojo.callback.event.DingMiniH5EventPayload;

/**
 * @author Mavin
 * @date 2024/7/3 18:09
 * @description 处理钉钉H5微应用订阅的事件回调处理器定义
 */
public interface IDingMiniH5EventCallbackHandler {

    /**
     * 获取适用的钉钉微应用
     *
     * @return 钉钉微应用
     */
    IDingMiniH5 getApp();

    /**
     * 判断处理器是否支持该事件
     *
     * @param payload 事件回调的参数
     * @return 是否支持
     */
    boolean support(DingMiniH5EventPayload payload);

    /**
     * 处理事件
     *
     * @param payload 事件回调的参数
     * @return 处理结果
     */
    boolean process(DingMiniH5EventPayload payload);

    /**
     * 获取执行优先级
     *
     * @return 执行优先级
     */
    default int order() {return 1;}

}
