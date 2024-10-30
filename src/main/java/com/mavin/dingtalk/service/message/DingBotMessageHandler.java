package com.mavin.dingtalk.service.message;

import cn.hutool.core.util.ObjectUtil;
import com.aliyun.dingtalkim_1_0.models.*;
import com.aliyun.dingtalkrobot_1_0.Client;
import com.aliyun.dingtalkrobot_1_0.models.*;
import com.aliyun.teautil.models.RuntimeOptions;
import com.mavin.dingtalk.component.SDKVersion;
import com.mavin.dingtalk.component.application.IDingMiniH5;
import com.mavin.dingtalk.pojo.message.bot.IDingBotMsg;
import com.mavin.dingtalk.pojo.message.interactive.IDingInteractiveMsg;
import com.mavin.dingtalk.service.AbstractDingApiHandler;
import com.mavin.dingtalk.service.IDingBotMessageHandler;
import com.mavin.dingtalk.service.IDingInteractiveMessageHandler;
import lombok.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mavin
 * @date 2024/6/27 15:11
 * @description 钉钉企业内部机器人消息服务
 */
public class DingBotMessageHandler extends AbstractDingApiHandler implements IDingBotMessageHandler, IDingInteractiveMessageHandler {

    private final IDingMiniH5 miniH5App;

    public DingBotMessageHandler(@NonNull IDingMiniH5 miniH5) {
        super(miniH5);
        this.miniH5App = miniH5;
    }

    public BatchSendOTOResponseBody sendInternalBotMsgToToIndividual(@NonNull List<String> userIds, @NonNull IDingBotMsg msg) {
        BatchSendOTOHeaders headers = new BatchSendOTOHeaders()
                .setXAcsDingtalkAccessToken(getAccessToken(SDKVersion.NEW));
        BatchSendOTORequest request = new BatchSendOTORequest()
                .setRobotCode(miniH5App.getRobotCode())
                .setUserIds(userIds)
                .setMsgKey(msg.getMsgKey())
                .setMsgParam(msg.getMsg());
        return execute(
                Client.class,
                client -> client.batchSendOTOWithOptions(request, headers, new RuntimeOptions()).getBody(),
                () -> "企业内部机器人批量发送单聊消息"
        );
    }

    @Override
    public OrgGroupSendResponseBody sendInternalBotMsgToGroup(
            @NonNull String openConversationId, String coolAppCode, @NonNull IDingBotMsg msg
    ) {
        OrgGroupSendHeaders headers = new OrgGroupSendHeaders()
                .setXAcsDingtalkAccessToken(getAccessToken(SDKVersion.NEW));
        OrgGroupSendRequest request = new OrgGroupSendRequest()
                .setRobotCode(miniH5App.getRobotCode())
                .setOpenConversationId(openConversationId)
                .setCoolAppCode(coolAppCode)
                .setMsgKey(msg.getMsgKey())
                .setMsgParam(msg.getMsg());
        return execute(
                Client.class,
                client -> client.orgGroupSendWithOptions(request, headers, new RuntimeOptions()).getBody(),
                () -> "企业内部机器人发送群聊消息"
        );
    }


    @Override
    public String sendInteractiveMsgToIndividual(
            @NonNull List<String> userIds, @NonNull IDingInteractiveMsg interactiveMsg, boolean supportForward
    ) {
        SendInteractiveCardHeaders headers = new SendInteractiveCardHeaders()
                .setXAcsDingtalkAccessToken(getAccessToken(SDKVersion.NEW));
        // 卡片操作配置
        SendInteractiveCardRequest.SendInteractiveCardRequestCardOptions cardOptions =
                new SendInteractiveCardRequest.SendInteractiveCardRequestCardOptions()
                        .setSupportForward(supportForward);
        // 卡片公有内容
        SendInteractiveCardRequest.SendInteractiveCardRequestCardData cardData =
                new SendInteractiveCardRequest.SendInteractiveCardRequestCardData()
                        .setCardParamMap(interactiveMsg.getMsg());
        SendInteractiveCardRequest request = new SendInteractiveCardRequest()
                .setCardTemplateId(interactiveMsg.getCardTemplateId())
                .setReceiverUserIdList(userIds)
                .setConversationType(0)
                .setCallbackRouteKey(interactiveMsg.getCallbackRouteKey())
                .setCardData(cardData)
                .setPrivateData(createInteractivePrivateDataValue(interactiveMsg.getPrivateData()))
                .setCardOptions(cardOptions)
                .setOutTrackId(interactiveMsg.getOutTrackId());
        final SendInteractiveCardResponse response = execute(
                com.aliyun.dingtalkim_1_0.Client.class,
                client -> client.sendInteractiveCardWithOptions(request, headers, new RuntimeOptions()),
                () -> "发送互动卡片高级版至个人"
        );
        return response.getBody().getResult().getProcessQueryKey();
    }

    @Override
    public String sendInteractiveMsgToGroup(
            @NonNull String openConversationId, @NonNull IDingInteractiveMsg interactiveMsg, boolean supportForward
    ) {
        SendInteractiveCardHeaders headers = new SendInteractiveCardHeaders()
                .setXAcsDingtalkAccessToken(getAccessToken(SDKVersion.NEW));
        // 卡片操作配置
        SendInteractiveCardRequest.SendInteractiveCardRequestCardOptions cardOptions =
                new SendInteractiveCardRequest.SendInteractiveCardRequestCardOptions()
                        .setSupportForward(supportForward);
        // 卡片公有内容
        SendInteractiveCardRequest.SendInteractiveCardRequestCardData cardData =
                new SendInteractiveCardRequest.SendInteractiveCardRequestCardData()
                        .setCardParamMap(interactiveMsg.getMsg());
        // 卡片私有数据
        Map<String, PrivateDataValue> interactiveMsgPrivateData =
                createInteractivePrivateDataValue(interactiveMsg.getPrivateData());
        SendInteractiveCardRequest request = new SendInteractiveCardRequest()
                .setCardTemplateId(interactiveMsg.getCardTemplateId())
                .setConversationType(1)
                .setOpenConversationId(openConversationId)
                .setRobotCode(miniH5App.getRobotCode())
                .setCallbackRouteKey(interactiveMsg.getCallbackRouteKey())
                .setCardData(cardData)
                .setPrivateData(interactiveMsgPrivateData)
                .setCardOptions(cardOptions)
                .setOutTrackId(interactiveMsg.getOutTrackId());
        final SendInteractiveCardResponse response = execute(
                com.aliyun.dingtalkim_1_0.Client.class,
                client -> client.sendInteractiveCardWithOptions(request, headers, new RuntimeOptions()),
                () -> "发送互动卡片高级版至群聊"
        );
        return response.getBody().getResult().getProcessQueryKey();
    }

    @Override
    public String updateInteractiveMsg(@NonNull IDingInteractiveMsg interactiveMsg) {
        UpdateInteractiveCardHeaders headers = new UpdateInteractiveCardHeaders()
                .setXAcsDingtalkAccessToken(getAccessToken(SDKVersion.NEW));
        // 默认
        UpdateInteractiveCardRequest.UpdateInteractiveCardRequestCardOptions cardOptions = new UpdateInteractiveCardRequest.UpdateInteractiveCardRequestCardOptions()
                .setUpdateCardDataByKey(true)
                .setUpdatePrivateDataByKey(true);
        UpdateInteractiveCardRequest.UpdateInteractiveCardRequestCardData cardData = new UpdateInteractiveCardRequest.UpdateInteractiveCardRequestCardData()
                .setCardParamMap(interactiveMsg.getMsg());
        UpdateInteractiveCardRequest request = new UpdateInteractiveCardRequest()
                .setOutTrackId(interactiveMsg.getOutTrackId())
                .setCardData(cardData)
                .setPrivateData(createInteractivePrivateDataValue(interactiveMsg.getPrivateData()))
                .setUserIdType(1)
                .setCardOptions(cardOptions);
        final UpdateInteractiveCardResponse response = execute(
                com.aliyun.dingtalkim_1_0.Client.class,
                client -> client.updateInteractiveCardWithOptions(request, headers, new RuntimeOptions()),
                () -> "更新互动卡片高级版"
        );
        return response.getBody().getSuccess();
    }

    private Map<String, PrivateDataValue> createInteractivePrivateDataValue(Map<String, Map<String, String>> privateData) {
        if (ObjectUtil.isNull(privateData)) {
            return null;
        }
        Map<String, PrivateDataValue> interactiveMsgPrivateData = new HashMap<>(16);
        for (Map.Entry<String, Map<String, String>> privateEntry : privateData.entrySet()) {
            PrivateDataValue privateDataValue = new PrivateDataValue();
            String key = privateEntry.getKey();
            Map<String, String> value = privateEntry.getValue();
            privateDataValue.setCardParamMap(value);
            interactiveMsgPrivateData.put(key, privateDataValue);
        }
        return interactiveMsgPrivateData;
    }

}
