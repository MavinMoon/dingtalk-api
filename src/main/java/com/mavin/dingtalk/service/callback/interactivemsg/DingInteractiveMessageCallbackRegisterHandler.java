package com.mavin.dingtalk.service.callback.interactivemsg;

import com.dingtalk.api.request.OapiImChatScencegroupInteractivecardCallbackRegisterRequest;
import com.mavin.dingtalk.component.application.IDingMiniH5;
import com.mavin.dingtalk.pojo.message.interactive.callback.IDingInteractiveCardCallBack;
import com.mavin.dingtalk.service.AbstractDingApiHandler;
import com.mavin.dingtalk.service.IDingInteractiveMessageCallbackRegisterHandler;
import com.mavin.dingtalk.constant.DingUrlConstant;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Mavin
 * @date 2024/6/28 15:48
 * @description 互动卡片注册回调地址服务
 */
@Slf4j
public class DingInteractiveMessageCallbackRegisterHandler  extends AbstractDingApiHandler implements IDingInteractiveMessageCallbackRegisterHandler {
    public DingInteractiveMessageCallbackRegisterHandler(@NonNull IDingMiniH5 app) {
        super(app);
    }

    @Override
    public void registerCallBackUrl(@NonNull IDingInteractiveCardCallBack callback, boolean forceUpdate) {
        log.debug("注册回调地址,应用:{},路由键:{},路由地址:{}", app.getAppName(), callback.getCallbackRouteKey(), callback.getCallbackUrl());
        OapiImChatScencegroupInteractivecardCallbackRegisterRequest request = new OapiImChatScencegroupInteractivecardCallbackRegisterRequest();
        request.setCallbackUrl(callback.getCallbackUrl());
        request.setApiSecret(callback.getApiSecret());
        request.setCallbackRouteKey(callback.getCallbackRouteKey());
        request.setForceUpdate(forceUpdate);
        execute(
                DingUrlConstant.REGISTER_CALLBACK,
                request,
                () -> "注册互动卡片高级版回调地址"
        );
    }
}
