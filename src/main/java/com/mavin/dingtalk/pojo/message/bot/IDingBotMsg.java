package com.mavin.dingtalk.pojo.message.bot;

import com.mavin.dingtalk.pojo.message.IDingMsg;
import com.google.gson.GsonBuilder;
import com.taobao.api.internal.util.StringUtils;

/**
 * @author Mavin
 * @date 2024/6/27 10:56
 * @description Messages that can be only sent by IDingBot.
 */
public interface IDingBotMsg extends IDingMsg<String> {

    /**
     * <a href="https://open.dingtalk.com/document/orgapp/types-of-messages-sent-by-robots">企业机器人发送消息的消息类型</a>
     *
     * @return msgKey
     */
    @Override
    default String getMsgKey() {
        return StringUtils.toCamelStyle(this.getClass().getSimpleName());
    }

    /**
     * <a href="https://open.dingtalk.com/document/orgapp/types-of-messages-sent-by-robots">企业机器人发送消息的消息类型</a>
     *
     * @return msgParam
     */
    @Override
    default String getMsg() {
        return new GsonBuilder().disableHtmlEscaping().create().toJson(this);
    }

}
