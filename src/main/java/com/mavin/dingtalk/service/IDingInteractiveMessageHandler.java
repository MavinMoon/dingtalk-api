package com.mavin.dingtalk.service;

import com.mavin.dingtalk.pojo.message.interactive.IDingInteractiveMsg;
import lombok.NonNull;

import java.util.List;

/**
 * @author Mavin
 * @date 2024/6/28 15:56
 * @description 钉钉互动卡片消息
 */
public interface IDingInteractiveMessageHandler {


    /**
     * 发送互动卡片至单聊
     * 机器人对用户单聊
     *
     * @param userIds        接收用户
     * @param interactiveMsg 卡片消息：包含必要配置信息：卡片id，callbackUrl等
     * @param supportForward  是否支持转发
     * @return processQueryKey 用于业务方后续查看已读列表的查询key
     */
    String sendInteractiveMsgToIndividual(
            @NonNull List<String> userIds, @NonNull IDingInteractiveMsg interactiveMsg, boolean supportForward
    );

    /**
     * 发送互动卡片至群聊
     * 机器人对群聊发送卡片，暂不支持艾特
     *
     * @param openConversationId 群聊id
     * @param interactiveMsg     卡片消息：包含必要配置信息：卡片id，callbackUrl等
     * @return processQueryKey 用于业务方后续查看已读列表的查询key
     */
    String sendInteractiveMsgToGroup(
            @NonNull String openConversationId, @NonNull IDingInteractiveMsg interactiveMsg, boolean supportForward
    );

    /**
     * 更新互动卡片，按key更新cardData数据
     *
     * @param interactiveMsg     卡片消息：包含必要配置信息：卡片id，callbackUrl等
     * @return success 更新结果
     */
    String updateInteractiveMsg(@NonNull IDingInteractiveMsg interactiveMsg);

}
