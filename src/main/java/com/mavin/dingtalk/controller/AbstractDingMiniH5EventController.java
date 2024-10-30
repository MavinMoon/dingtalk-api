package com.mavin.dingtalk.controller;

import com.mavin.dingtalk.component.application.IDingMiniH5;
import com.mavin.dingtalk.component.configuration.IDingMiniH5EventCallbackConfig;
import com.mavin.dingtalk.pojo.callback.request.DingMiniH5EventCallbackRequest;
import com.mavin.dingtalk.utils.DingCallbackCrypto;
import com.google.common.base.Preconditions;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author Mavin
 * @date 2024/7/6 12:11
 * @description 钉钉H5微应用控制器基类
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractDingMiniH5EventController {

    protected final IDingMiniH5EventCallbackConfig callbackConfiguration;

    protected DingCallbackCrypto callbackCrypto;

    /**
     * 钉钉事件回调Http请求解密
     *
     * @param request 回调接口 query parameters
     * @param payload 回调接口 body
     * @return 解密后内容
     * @throws DingCallbackCrypto.DingTalkEncryptException
     */
    protected String decrypt(DingMiniH5EventCallbackRequest request, EventPayload payload) throws DingCallbackCrypto.DingTalkEncryptException {
        Preconditions.checkArgument(Objects.nonNull(callbackConfiguration), "请实现DingMiniH5EventCallbackConfig并提供一个有效的钉钉miniH5微应用实例");
        final IDingMiniH5 app = callbackConfiguration.getApp();
        Preconditions.checkArgument(Objects.nonNull(app), "钉钉miniH5微应用实例为空");
        String encryptMsg = payload.getEncrypt();
        // 钉钉提供的加解密工具
        callbackCrypto = new DingCallbackCrypto(app.getEventToken(), app.getEventKey(), app.getClientSecret());
        return callbackCrypto.getDecryptMsg(request.getMsg_signature(), request.getTimestamp(), request.getNonce(), encryptMsg);
    }

    @Data
    public static class EventPayload {
        private String encrypt;
    }

}
