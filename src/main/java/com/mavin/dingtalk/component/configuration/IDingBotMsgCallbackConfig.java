package com.mavin.dingtalk.component.configuration;

import com.mavin.dingtalk.component.application.IDingMiniH5;

/**
 * @author Mavin
 * @date 2024/7/8 15:11
 * @description 钉钉机器人消息回调配置，客户端如果使用stream模式并且需要接入钉钉机器人消息回调时必须实现该接口
 */
public interface IDingBotMsgCallbackConfig {

    /**
     * @return 钉钉H5微应用
     */
    IDingMiniH5 getApp();

}
