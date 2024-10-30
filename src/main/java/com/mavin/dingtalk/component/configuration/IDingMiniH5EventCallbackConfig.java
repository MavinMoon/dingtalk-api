package com.mavin.dingtalk.component.configuration;

import com.mavin.dingtalk.component.application.IDingMiniH5;

/**
 * @author Mavin
 * @date 2024/6/24 17:48
 * @description 钉钉小程序事件回调配置，客户端需要接入钉钉事件回调时必须实现该接口
 */
public interface IDingMiniH5EventCallbackConfig {

    /**
     * @return 钉钉H5微应用
     */
    IDingMiniH5 getApp();

}
