package com.mavin.dingtalk.constant.minih5event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author Mavin
 * @date 2024/7/3 17:28
 * @description 事件订阅总览
 * @See https://open.dingtalk.com/document/orgapp/org-event-overview
 */
@Getter
@AllArgsConstructor
public enum DingMiniH5Event {


    /***/
    CHECK_URL("check_url", "检查配置URL事件"),

    ;

    private final String eventType;

    private final String eventName;

    public static DingMiniH5Event getByEventType(String eventType) {
        return Arrays.stream(DingMiniH5Event.values())
                .filter(dingMiniH5Event -> dingMiniH5Event.getEventType().equals(eventType))
                .findFirst()
                .orElse(null);
    }

}
