package com.mavin.dingtalk.component.application;

import com.mavin.dingtalk.annotation.MethodDesc;

/**
 * @author Mavin
 * @date 2024/6/24 14:50
 * @description 钉钉miniH5企业内部应用
 */
public interface IDingMiniH5 extends IDingApp {

    /**
     * 事件回调配置, HTTP推送, 加密 aes_key
     */
    @MethodDesc(value = "事件回调，HTTP推送，加密 aes_key")
    default String getEventKey() {
        return null;
    }

    /**
     * 事件回调配置, HTTP推送, 签名 token
     */
    @MethodDesc(value = "事件回调，HTTP推送，签名 token")
    default String getEventToken() {
        return null;
    }

    /**
     * 企业内部应用机器人唯一编码
     *
     * @return 机器人唯一编码
     */
    @MethodDesc(value = "企业内部应用机器人唯一编码（在旧版机器人应用中为AppKey）")
    default String getRobotCode() {
        return null;
    }

}
