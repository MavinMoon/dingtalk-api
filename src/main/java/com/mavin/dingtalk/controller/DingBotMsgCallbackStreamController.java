package com.mavin.dingtalk.controller;

import cn.hutool.core.util.ObjectUtil;
import com.dingtalk.open.app.api.OpenDingTalkClient;
import com.dingtalk.open.app.api.OpenDingTalkStreamClientBuilder;
import com.dingtalk.open.app.api.callback.DingTalkStreamTopics;
import com.dingtalk.open.app.api.callback.OpenDingTalkCallbackListener;
import com.dingtalk.open.app.api.models.bot.ChatbotMessage;
import com.dingtalk.open.app.api.security.AuthClientCredential;
import com.mavin.dingtalk.component.application.IDingMiniH5;
import com.mavin.dingtalk.component.configuration.IDingBotMsgCallbackConfig;
import com.mavin.dingtalk.component.exception.DingApiException;
import com.mavin.dingtalk.service.callback.bot.IDingBotMsgCallBackHandler;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import shade.com.alibaba.fastjson2.JSONObject;

import java.util.List;

/**
 * @author Mavin
 * @date 2024/7/8 12:16
 * @description 默认钉钉机器人回调消息Stream模式控制器
 */
@Component
@Slf4j
@ConditionalOnProperty(prefix = "dingtalk.miniH5.bot", name = "streamEnabled", havingValue = "true")
public class DingBotMsgCallbackStreamController extends AbstractDingBotMsgCallbackController<ChatbotMessage> implements SmartInitializingSingleton {

    private final IDingBotMsgCallbackConfig callbackConfig;

    public DingBotMsgCallbackStreamController(
            IDingBotMsgCallbackConfig callbackConfig,
            List<IDingBotMsgCallBackHandler<ChatbotMessage>> dingBotMsgCallBackHandlers
    ) {
        super(dingBotMsgCallBackHandlers);
        this.callbackConfig = callbackConfig;
    }


    @Override
    public void afterSingletonsInstantiated() {
        Preconditions.checkArgument(ObjectUtil.isNotNull(callbackConfig),
                "请实现IDingBotMsgCallbackConfig并提供一个有效的钉钉miniH5微应用实例");
        final IDingMiniH5 app = callbackConfig.getApp();
        Preconditions.checkArgument(ObjectUtil.isNotNull(app), "钉钉miniH5微应用实例为空");
        OpenDingTalkClient client = OpenDingTalkStreamClientBuilder.custom()
                //配置应用的身份信息, 企业内部应用分别为appKey和appSecret, 三方应用为suiteKey和suiteSecret
                .credential(new AuthClientCredential(app.getClientId(), app.getClientSecret()))
                //注册机器人回调
                .registerCallbackListener(
                        DingTalkStreamTopics.BOT_MESSAGE_TOPIC,
                        (OpenDingTalkCallbackListener<ChatbotMessage, JSONObject>) payload -> {
                            handlers(app, payload).ifPresent(
                                    handlers -> handlers.parallelStream().forEachOrdered(
                                            handler -> {
                                                if (!handler.handle(app, payload)) {
                                                    log.warn("机器人回调处理失败，handler:{}", handler.description());
                                                }
                                            }
                                    )
                            );
                            return new JSONObject();
                        }
                )
                .build();
        // 启动服务
        try {
            log.info("启动钉钉机器人消息回调监听默认Stream模式");
            client.start();
        } catch (Exception e) {
            log.error("启动钉钉机器人消息回调监听默认Stream模式失败", e);
            throw new DingApiException("启动钉钉机器人消息回调监听默认Stream模式失败", e);
        }
    }
}
