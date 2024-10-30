package com.mavin.dingtalk.pojo.callback.event;

import com.mavin.dingtalk.annotation.FieldDesc;
import com.mavin.dingtalk.annotation.MethodDesc;
import com.mavin.dingtalk.constant.minih5event.DingMiniH5Event;
import com.mavin.dingtalk.pojo.callback.eventbody.IDingMiniH5EventBody;
import com.google.gson.Gson;
import lombok.Value;

/**
 * @author Mavin
 * @date 2024/7/3 17:17
 * @description 钉钉事件回调数据承载体
 */
@Value
public class DingMiniH5EventPayload {

    @FieldDesc("为了方便客户端调用, 增加一个系统内使用到的钉钉事件")
    DingMiniH5Event eventType;

    @FieldDesc("消息负载的特殊性, 其中包含的字段与具体的事件有关, 在获取时由客户端决定")
    String payload;

    @FieldDesc("唯一应用id")
    String appId;

    @MethodDesc("客户端决定转什么类型")
    public <T extends IDingMiniH5EventBody> T getPayload(Class<T> clz) {
        return new Gson().fromJson(payload, clz);
    }


}
