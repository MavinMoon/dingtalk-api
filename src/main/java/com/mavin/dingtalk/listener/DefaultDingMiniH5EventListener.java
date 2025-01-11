package com.mavin.dingtalk.listener;

import com.mavin.dingtalk.constant.minih5event.DingMiniH5Event;
import com.mavin.dingtalk.pojo.callback.event.DingMiniH5EventPayload;
import com.mavin.dingtalk.service.callback.minih5.IDingMiniH5EventCallbackHandler;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

/**
 * @author Mavin
 * @date 2024/7/4 15:03
 * @description 默认的钉钉微应用事件订阅-系统内事件监听器，默认使用异步处理任务
 */
@Slf4j
public class DefaultDingMiniH5EventListener {

    private final List<IDingMiniH5EventCallbackHandler> miniH5EventCallbackHandlers;
    private final ExecutorService defaultDingTalkExecutor;

    public DefaultDingMiniH5EventListener(
            List<IDingMiniH5EventCallbackHandler> miniH5EventCallbackHandlers,
            @Qualifier("defaultDingTalkExecutor") ExecutorService defaultDingTalkExecutor
    ) {
        this.miniH5EventCallbackHandlers = miniH5EventCallbackHandlers;
        this.defaultDingTalkExecutor = defaultDingTalkExecutor;
    }

    /**
     * 注意幂等
     */
    @EventListener
    public void handleMiniH5EventCallback(DingMiniH5EventPayload payload) {
        final DingMiniH5Event eventType = payload.getEventType();
        log.info("使用插件自带默认监听器处理钉钉微应用事件回调:{}({})", eventType.getEventType(), eventType.getEventName());
        if (CollectionUtils.isEmpty(miniH5EventCallbackHandlers)) {
            log.error("容器中没有找到任何IDingMiniH5EventCallbackHandler实例");
            return;
        }
        miniH5EventCallbackHandlers
                .stream()
                .filter(handler -> eventType.getEventType().equals(handler.getSupportEvent().getEventType()))
                .collect(Collectors.groupingBy(IDingMiniH5EventCallbackHandler::order))
                .entrySet()
                .stream()
                .map(entry -> new ExecutorWithLevel(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(ExecutorWithLevel::getOrder))
                .forEach(executorWithLevel -> {
                    try {
                        final List<Task> tasks = executorWithLevel.handlers.stream()
                                .map(i -> new Task(payload, i))
                                .collect(Collectors.toList());
                        defaultDingTalkExecutor.invokeAll(tasks);
                    } catch (Exception e) {
                        log.error("执行对钉钉微应用事件:{}({})异常", eventType.getEventType(), eventType.getEventName(), e);
                    }
                });
    }

    @Value
    private static class ExecutorWithLevel {
        Integer order;
        List<IDingMiniH5EventCallbackHandler> handlers;
    }

    private static class Task implements Callable<Void> {
        private final DingMiniH5EventPayload payload;
        private final IDingMiniH5EventCallbackHandler handler;

        public Task(DingMiniH5EventPayload payload, IDingMiniH5EventCallbackHandler handler) {
            this.payload = payload;
            this.handler = handler;
        }

        @Override
        public Void call() {
            final boolean process = handler.process(payload);
            log.debug("钉钉微应用事件处理器处理结果:{}", process);
            return null;
        }
    }

}
