package com.mavin.dingtalk.controller;

import cn.hutool.core.util.ObjectUtil;
import com.dingtalk.open.app.api.OpenDingTalkClient;
import com.dingtalk.open.app.api.OpenDingTalkStreamClientBuilder;
import com.dingtalk.open.app.api.security.AuthClientCredential;
import com.dingtalk.open.app.stream.protocol.event.EventAckStatus;
import com.mavin.dingtalk.component.application.IDingMiniH5;
import com.mavin.dingtalk.component.configuration.IDingMiniH5EventCallbackConfig;
import com.mavin.dingtalk.component.exception.DingApiException;
import com.mavin.dingtalk.constant.minih5event.DingMiniH5Event;
import com.mavin.dingtalk.pojo.callback.event.DingMiniH5EventPayload;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author Mavin
 * @date 2024/7/4 14:50
 * @description 默认的钉钉H5微应用事件回调Stream模式控制器
 */
@Slf4j
@Component
@ConditionalOnMissingBean
@ConditionalOnProperty(prefix = "dingtalk.miniH5.event", name = "streamEnabled", havingValue = "true")
public class DingMiniH5EventStreamController extends AbstractDingMiniH5EventController implements SmartInitializingSingleton {

    private final ApplicationEventPublisher eventPublisher;

    public DingMiniH5EventStreamController(
            IDingMiniH5EventCallbackConfig callbackConfiguration, ApplicationEventPublisher eventPublisher
    ) {
        super(callbackConfiguration);
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void afterSingletonsInstantiated() {
        Preconditions.checkArgument(ObjectUtil.isNotNull(callbackConfiguration),
                "请实现IDingMiniH5EventCallbackConfig并提供一个有效的钉钉miniH5微应用实例");
        final IDingMiniH5 app = callbackConfiguration.getApp();
        Preconditions.checkArgument(ObjectUtil.isNotNull(app), "钉钉miniH5微应用实例为空");
        OpenDingTalkClient client = OpenDingTalkStreamClientBuilder.custom()
                .credential(new AuthClientCredential(app.getClientId(), app.getClientSecret()))
                .registerAllEventListener(event -> {
                    log.debug("收到钉钉事件:{}", event.toString());
                    try {
                        // 事件类型
                        String eventType = event.getEventType();
                        // 获取事件体
                        String body = event.getData().toJSONString();
                        final DingMiniH5Event eventEnum = DingMiniH5Event.getByEventType(eventType);
                        Preconditions.checkNotNull(eventEnum, "该插件模块暂不支持处理该类钉钉事件:" + eventType);
                        log.info("发生了:{}({})事件, 开始广播事件", eventEnum.getEventType(), eventEnum.getEventName());
                        eventPublisher.publishEvent(new DingMiniH5EventPayload(eventEnum, body, app.getAppId()));
                        return EventAckStatus.SUCCESS;
                    } catch (Exception e) {
                        // 消费失败
                        log.error("处理钉钉事件异常", e);
                        return EventAckStatus.LATER;
                    }
                })
                .build();
        // 启动服务
        try {
            log.info("启动钉钉H5微应用事件回调监听默认Stream模式");
            client.start();
        } catch (Exception e) {
            log.error("启动钉钉H5微应用事件回调监听默认Stream模式失败", e);
            throw new DingApiException("启动钉钉H5微应用事件回调监听默认Stream模式失败", e);
        }
    }
}
