package com.mavin.dingtalk.service.callback.minih5;

import com.mavin.dingtalk.component.application.IDingMiniH5;
import com.mavin.dingtalk.constant.minih5event.DingMiniH5Event;
import com.mavin.dingtalk.pojo.callback.event.DingMiniH5EventPayload;
import com.mavin.dingtalk.pojo.callback.eventbody.IDingMiniH5EventBody;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

/**
 * @author Mavin
 * @date 2024/7/3 18:11
 * @description 钉钉H5事件监听回调处理基类
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractDingMiniH5EventCallbackHandler<T extends IDingMiniH5EventBody> implements IDingMiniH5EventCallbackHandler {

    /**
     * 适用钉钉应用
     */
    @Getter
    private final IDingMiniH5 app;

    /**
     * 处理器对应的钉钉事件类型
     */
    @Getter
    private final DingMiniH5Event supportEvent;

    @Override
    public boolean support(DingMiniH5EventPayload payload) {
        Preconditions.checkNotNull(payload);
        Preconditions.checkNotNull(payload.getEventType());
        Preconditions.checkNotNull(payload.getAppId());
        final Class<T> clazz = getFirstTypeArguments();
        T body = payload.getPayload(clazz);
        Preconditions.checkNotNull(body, "IDingMiniH5EventBody: the body is null");
        return doSupport(body);
    }

    @Override
    public boolean process(DingMiniH5EventPayload payload) {
        final DingMiniH5Event eventType = payload.getEventType();
        Preconditions.checkNotNull(eventType, "插件暂不支持该钉钉回调事件！");
        Preconditions.checkArgument(payload.getAppId().equals(app.getAppId()), "该事件回调处理器与钉钉应用不匹配");
        log.info("客户端{}接收到钉钉事件:{}({})", this.getClass().getSimpleName(), eventType.getEventType(), eventType.getEventName());
        if (!support(payload)) {
            return false;
        }
        try {
            final Class<T> clazz = getFirstTypeArguments();
            T body = payload.getPayload(clazz);
            if (log.isDebugEnabled()) {
                final Stopwatch stopwatch = Stopwatch.createStarted();
                final boolean result = doProcess(body);
                stopwatch.stop();
                log.debug("客户端业务处理钉钉事件:{}({})耗时:{}ms", eventType.getEventType(), eventType.getEventName(), stopwatch.elapsed(TimeUnit.MILLISECONDS));
                return result;
            } else {
                return doProcess(body);
            }
        } catch (Exception e) {
            log.error("处理钉钉事件:{}({})失败", eventType.getEventType(), eventType.getEventName(), e);
        }
        return false;
    }


    /**
     * Implementations should check if the handler supports
     * processing the event type and the template id.
     *
     * @param body event payload
     * @return whether the handler supports processing this kind of event
     */
    protected abstract boolean doSupport(T body);

    /**
     * Process event accordingly
     *
     * @param body event payload
     * @return successfully processed or not
     */
    protected abstract boolean doProcess(T body);

    /**
     * 获取第一个泛型参数Class类型
     *
     * @return the first type argument
     */
    @SuppressWarnings("unchecked")
    protected Class<T> getFirstTypeArguments() {
        final ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        final Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        return (Class<T>) actualTypeArguments[0];
    }

}
