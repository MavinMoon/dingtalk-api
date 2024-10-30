package com.mavin.dingtalk.component;

import cn.hutool.core.collection.CollUtil;
import com.mavin.dingtalk.pojo.message.interactive.callback.IDingInteractiveCardCallBack;
import com.mavin.dingtalk.service.callback.interactivemsg.DingInteractiveMessageCallbackRegisterHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * @author Mavin
 * @date 2024/6/28 15:39
 * @description 钉钉启动器
 */
@Async
@Slf4j
@RequiredArgsConstructor
public class DingBooter implements ApplicationListener<ApplicationReadyEvent> {

    private final List<IDingInteractiveCardCallBack> callBacks;

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent applicationReadyEvent) {
        if (CollUtil.isEmpty(callBacks)) {
            return;
        }
        callBacks.forEach(callBack ->
                callBack.getDingMiniH5Apps()
                        .forEach(app -> {
                            final DingInteractiveMessageCallbackRegisterHandler registerHandler = new DingInteractiveMessageCallbackRegisterHandler(app);
                            registerHandler.registerCallBackUrl(callBack, true);
                        })
        );
    }

}
