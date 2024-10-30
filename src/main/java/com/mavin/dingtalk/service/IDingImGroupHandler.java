package com.mavin.dingtalk.service;

import com.aliyun.dingtalkim_1_0.models.BatchQueryGroupMemberResponseBody;
import com.aliyun.dingtalkim_1_0.models.ChatIdToOpenConversationIdResponseBody;
import com.dingtalk.api.response.OapiChatGetResponse;
import com.dingtalk.api.response.OapiImChatScenegroupGetResponse;
import lombok.NonNull;

/**
 * @author Mavin
 * @date 2024/6/25 17:40
 * @description 钉钉群相关接口
 */
public interface IDingImGroupHandler {

    /**
     * 根据openConversationId和钉钉酷应用Code获取群（V2）成员列表
     *
     * @param openConversationId 群ID
     * @param coolAppCode        钉钉酷应用Code
     * @param maxResults         分页大小
     * @param nextToken          分页游标
     * @return 群成员列表
     */
    BatchQueryGroupMemberResponseBody getSceneGroupMemberList(
            @NonNull String openConversationId, String coolAppCode, @NonNull Long maxResults, String nextToken
    );

    /**
     * 根据chatId获取openConversationId
     *
     * @param chatId 群ID
     * @return openConversationId
     */
    ChatIdToOpenConversationIdResponseBody getOpenConversationIdByChatId(@NonNull String chatId);

    /**
     * 根据openConversationId获取群信息
     *
     * @param openConversationId 群ID
     * @return 群信息
     */
    OapiImChatScenegroupGetResponse.DecorationGroupQueryResponse getGroupInfoByOpenConversationId(@NonNull String openConversationId);

    /**
     * 根据chatId获取群信息
     *
     * @param chatId 群ID
     * @return 群信息
     */
    OapiChatGetResponse.ChatInfo getGroupInfoByChatId(@NonNull String chatId);

}
