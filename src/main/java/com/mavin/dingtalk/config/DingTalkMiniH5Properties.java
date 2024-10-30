package com.mavin.dingtalk.config;

import lombok.Data;

/**
 * @author Mavin
 * @date 2024/6/21 17:17
 * @description
 */
@Data
public class DingTalkMiniH5Properties {

    private EventCallbackConfig event;

    private BotCallbackConfig bot;

    @Data
    public static class EventCallbackConfig {
        private boolean httpEnabled;
        private boolean streamEnabled;
        private ListenerConfig listener;
    }

    @Data
    public static class ListenerConfig {
        private boolean defaultEnabled;
    }

    @Data
    public static class BotCallbackConfig {
        private boolean httpEnabled;
        private boolean streamEnabled;
    }

}
