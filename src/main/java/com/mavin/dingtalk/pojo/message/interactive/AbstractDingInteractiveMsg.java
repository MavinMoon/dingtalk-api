package com.mavin.dingtalk.pojo.message.interactive;


import cn.hutool.core.text.CharSequenceUtil;
import com.mavin.dingtalk.annotation.FieldDesc;
import com.mavin.dingtalk.pojo.message.interactive.callback.IDingInteractiveCardCallBack;
import com.google.common.base.Preconditions;
import lombok.Data;

import java.util.Map;
import java.util.Objects;

/**
 * @author Mavin
 * @date 2024/6/28 14:53
 * @description 钉钉互动消息抽象类，客户端需要实现该类并填充卡片的自定义字段
 */
@Data
public abstract class AbstractDingInteractiveMsg implements IDingInteractiveMsg {

    @FieldDesc("模版Id")
    protected String cardTemplateId;

    @FieldDesc("回调路由key")
    protected String callbackRouteKey;

    @FieldDesc("模版实例Id")
    protected String outTrackId;

    @FieldDesc("卡片私有数据，暂支持替换文本类型")
    protected Map<String, Map<String, String>> privateData;

    protected AbstractDingInteractiveMsg(String cardTemplateId, String outTrackId) {
        Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(cardTemplateId), "模版Id不能为空");
        Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(outTrackId), "模版实例Id不能为空");
        this.cardTemplateId = cardTemplateId;
        this.outTrackId = outTrackId;
    }

    protected AbstractDingInteractiveMsg(
            IDingInteractiveCardCallBack callBack, String cardTemplateId, String outTrackId
    ) {
        Preconditions.checkArgument(Objects.nonNull(callBack), "回调路由不能为空");
        Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(cardTemplateId), "模版Id不能为空");
        Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(outTrackId), "模版实例Id不能为空");
        this.callbackRouteKey = callBack.getCallbackRouteKey();
        this.outTrackId = outTrackId;
        this.cardTemplateId = cardTemplateId;
    }

    protected AbstractDingInteractiveMsg(String cardTemplateId, String outTrackId, Map<String, Map<String, String>> privateData) {
        Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(cardTemplateId), "模版Id不能为空");
        Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(outTrackId), "模版实例Id不能为空");
        Preconditions.checkArgument(Objects.nonNull(privateData), "卡片私有数据不能为空");
        this.cardTemplateId = cardTemplateId;
        this.outTrackId = outTrackId;
        this.privateData = privateData;
    }

    protected AbstractDingInteractiveMsg(
            IDingInteractiveCardCallBack callBack, String cardTemplateId, String outTrackId, Map<String, Map<String, String>> privateData
    ) {
        Preconditions.checkArgument(Objects.nonNull(callBack), "回调路由不能为空");
        Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(cardTemplateId), "模版Id不能为空");
        Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(outTrackId), "模版实例Id不能为空");
        Preconditions.checkArgument(Objects.nonNull(privateData), "卡片私有数据不能为空");
        this.callbackRouteKey = callBack.getCallbackRouteKey();
        this.outTrackId = outTrackId;
        this.cardTemplateId = cardTemplateId;
        this.privateData = privateData;
    }


}
