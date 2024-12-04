package com.mavin.dingtalk.controller;

import com.mavin.dingtalk.component.application.IDingMiniH5;
import com.mavin.dingtalk.component.configuration.IDingMiniH5EventCallbackConfig;
import com.mavin.dingtalk.constant.minih5event.DingMiniH5Event;
import com.mavin.dingtalk.pojo.callback.event.DingMiniH5EventPayload;
import com.mavin.dingtalk.pojo.callback.request.DingMiniH5EventCallbackRequest;
import com.mavin.dingtalk.utils.DingCallbackCrypto;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Mavin
 * @date 2024/7/8 15:30
 * @description 默认的钉钉H5微应用事件回调HTTP模式控制器
 */
@Slf4j
@RestController
@RequestMapping("/ding/miniH5/event")
@ConditionalOnProperty(prefix = "dingtalk.miniH5.event", name = "httpEnabled", havingValue = "true")
public class DingMiniH5EventHttpController extends AbstractDingMiniH5EventController {

    private final ApplicationEventPublisher eventPublisher;

    public DingMiniH5EventHttpController(
            IDingMiniH5EventCallbackConfig callbackConfiguration, ApplicationEventPublisher eventPublisher
    ) {
        super(callbackConfiguration);
        this.eventPublisher = eventPublisher;
    }

    @PostMapping("/callback")
    public Map<String, String> callback(
            // query parameters
            DingMiniH5EventCallbackRequest request,
            // application/json
            @RequestBody EventPayload payload
    ) {
        log.debug("钉钉事件订阅回调");
        log.debug(request.toString());
        log.debug(payload.toString());
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            /*
             * DingCallbackCrypto第三个参数 说明：
             * 1、开发者后台配置的订阅事件为应用级事件推送，此时OWNER_KEY为应用的APP_KEY。
             * 2、调用订阅事件接口订阅的事件为企业级事件推送，
             *     此时OWNER_KEY为：企业的appkey（企业内部应用）或SUITE_KEY（三方应用）
             */
            final String decryptMsg = decrypt(request, payload);
            log.debug("解密后的事件回调请求体:{}", decryptMsg);
            // 由于请求体跟事件类型相关, 不固定, 所以转Map取事件类型
            final Map<String, Object> decryptedMap = new GsonBuilder().create().fromJson(decryptMsg,
                    new TypeToken<Map<String, Object>>() {}.getType());
            final String eventType = (String) decryptedMap.get("EventType");
            final DingMiniH5Event eventEnum = DingMiniH5Event.getByEventType(eventType);
            Preconditions.checkNotNull(eventEnum, "该插件模块暂不支持处理该类钉钉事件:" + eventType);
            if (Objects.equals(DingMiniH5Event.CHECK_URL, eventEnum)) {
                // 测试回调url的正确性
                log.info(eventEnum.getEventName());
            } else {
                // 添加其他已注册的
                log.info("发生了:{}({})事件, 开始广播事件", eventEnum.getEventType(), eventEnum.getEventName());
                final IDingMiniH5 app = callbackConfiguration.getApp();
                eventPublisher.publishEvent(new DingMiniH5EventPayload(eventEnum, decryptMsg, app.getAppId()));
            }
            Map<String, String> successMap = callbackCrypto.getEncryptedMap("success");
            stopwatch.stop();
            log.debug("钉钉事件订阅回调耗时: {}ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
            return successMap;
        } catch (DingCallbackCrypto.DingTalkEncryptException e) {
            log.error("钉钉事件订阅回调失败{}{}", request, payload, e);
            return null;
        }
    }

}
