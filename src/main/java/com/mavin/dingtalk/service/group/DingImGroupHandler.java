package com.mavin.dingtalk.service.group;

import com.aliyun.dingtalkim_1_0.models.*;
import com.aliyun.teautil.models.RuntimeOptions;
import com.dingtalk.api.request.OapiChatGetRequest;
import com.dingtalk.api.request.OapiImChatScenegroupGetRequest;
import com.dingtalk.api.response.OapiChatGetResponse;
import com.dingtalk.api.response.OapiImChatScenegroupGetResponse;
import com.mavin.dingtalk.component.SDKVersion;
import com.mavin.dingtalk.component.application.IDingMiniH5;
import com.mavin.dingtalk.service.AbstractDingApiHandler;
import com.mavin.dingtalk.service.IDingImGroupHandler;
import com.mavin.dingtalk.constant.DingUrlConstant;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Mavin
 * @date 2024/6/25 17:47
 * @description 钉钉群组操作
 */
@Slf4j
public class DingImGroupHandler extends AbstractDingApiHandler implements IDingImGroupHandler {

    public DingImGroupHandler(@NonNull IDingMiniH5 app) {
        super(app);
    }

    @Override
    public BatchQueryGroupMemberResponseBody getSceneGroupMemberList(
            @NonNull String openConversationId, String coolAppCode, @NonNull Long maxResults, String nextToken
    ) {
        BatchQueryGroupMemberHeaders headers = new BatchQueryGroupMemberHeaders()
                .setXAcsDingtalkAccessToken(getAccessToken(SDKVersion.NEW));
        BatchQueryGroupMemberRequest request = new BatchQueryGroupMemberRequest()
                .setOpenConversationId(openConversationId)
                .setCoolAppCode(coolAppCode)
                .setMaxResults(maxResults)
                .setNextToken(nextToken);
        return execute(
                com.aliyun.dingtalkim_1_0.Client.class,
                client -> client.batchQueryGroupMemberWithOptions(request, headers, new RuntimeOptions()).getBody(),
                () -> "获取群成员信息列表（会话2.0）"
        );
    }

    @Override
    public ChatIdToOpenConversationIdResponseBody getOpenConversationIdByChatId(@NonNull String chatId) {
        ChatIdToOpenConversationIdHeaders headers = new ChatIdToOpenConversationIdHeaders()
                .setXAcsDingtalkAccessToken(getAccessToken(SDKVersion.NEW));
        return execute(
                com.aliyun.dingtalkim_1_0.Client.class,
                client -> client.chatIdToOpenConversationIdWithOptions(chatId, headers, new RuntimeOptions()).getBody(),
                () -> "根据ChatId获取OpenConversationId"
        );
    }

    @Override
    public OapiImChatScenegroupGetResponse.DecorationGroupQueryResponse getGroupInfoByOpenConversationId(@NonNull String openConversationId) {
        OapiImChatScenegroupGetRequest request = new OapiImChatScenegroupGetRequest();
        request.setOpenConversationId(openConversationId);
        return execute(DingUrlConstant.ImSceneGroup.GET_GROUP_INFO_BY_OPEN_CONVERSATION_ID, request, () -> "根据OpenConversationId获取群组信息").getResult();
    }

    @Override
    public OapiChatGetResponse.ChatInfo getGroupInfoByChatId(@NonNull String chatId) {
        OapiChatGetRequest request = new OapiChatGetRequest();
        request.setChatid(chatId);
        return execute(DingUrlConstant.ImSceneGroup.GET_GROUP_INFO_BY_CHAT_ID, request, () -> "根据ChatId获取群组信息").getChatInfo();
    }


}
