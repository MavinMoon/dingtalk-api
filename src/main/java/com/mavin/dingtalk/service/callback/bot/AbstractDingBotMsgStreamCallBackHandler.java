package com.mavin.dingtalk.service.callback.bot;

import cn.hutool.core.util.ObjectUtil;
import com.dingtalk.open.app.api.models.bot.ChatbotMessage;
import com.mavin.dingtalk.component.application.IDingMiniH5;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Mavin
 * @date 2024/7/8 18:23
 * @description 钉钉机器人Stream消息回调处理基类
 */
@Slf4j
public abstract class AbstractDingBotMsgStreamCallBackHandler implements IDingBotMsgCallBackHandler<ChatbotMessage> {

    @Override
    public boolean handle(IDingMiniH5 app, ChatbotMessage payload) {
        final String appName = app.getAppName();
        final String robotCode = app.getRobotCode();
        final String msgType = payload.getMsgtype();
        log.info("开始钉钉机器人Stream消息回调处理，应用：{}，机器人Code：{}，消息类型：{}", appName, robotCode, msgType);
        if (!authorized(app, payload)) {
            return false;
        }
        if (ObjectUtil.isNull(payload) || (ObjectUtil.isNull(payload.getText()) && ObjectUtil.isNull(payload.getContent()))) {
            return false;
        }
        try {
            return doHandler(app, payload);
        } catch (Exception e) {
            log.error("钉钉机器人消息回调处理失败，应用：{}，机器人Code：{}，消息类型：{}", appName, robotCode, msgType, e);
        } finally {
            log.info("钉钉机器人消息HTTP消息回调处理结束");
        }
        return false;
    }

    /**
     * 自定义执行逻辑
     *
     * @param app     钉钉H5微应用
     * @param payload 机器人消息回调请求体
     * @return 是否处理成功
     */
    protected abstract boolean doHandler(IDingMiniH5 app, ChatbotMessage payload);

}
