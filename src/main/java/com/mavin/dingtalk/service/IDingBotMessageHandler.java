package com.mavin.dingtalk.service;

import com.aliyun.dingtalkrobot_1_0.models.BatchSendOTOResponseBody;
import com.aliyun.dingtalkrobot_1_0.models.OrgGroupSendResponseBody;
import com.mavin.dingtalk.pojo.message.bot.IDingBotMsg;
import lombok.NonNull;

import java.util.List;

/**
 * @author Mavin
 * @date 2024/6/27 14:53
 * @description 钉钉机器人消息发送接口
 */
public interface IDingBotMessageHandler {

    /**
     * 通过钉钉用户ID发送机器人单聊消息
     *
     * @param userIds 钉钉用户id
     * @param msg     IDingBotMsg
     * @return BatchSendOTOResponseBody
     */
    BatchSendOTOResponseBody sendInternalBotMsgToToIndividual(@NonNull List<String> userIds, @NonNull IDingBotMsg msg);

    /**
     * 通过钉钉群组ID发送机器人群聊消息
     *
     * @param openConversationId 群聊id
     * @param coolAppCode 酷应用唯一编码
     * @param msg IDingBotMsg
     * @return OrgGroupSendResponseBody
     */
    OrgGroupSendResponseBody sendInternalBotMsgToGroup(
            @NonNull String openConversationId, String coolAppCode, @NonNull IDingBotMsg msg
    );

}
