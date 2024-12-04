package com.mavin.dingtalk.controller;

import com.mavin.dingtalk.component.application.IDingMiniH5;
import com.mavin.dingtalk.component.factory.app.DingAppFactory;
import com.mavin.dingtalk.pojo.callback.request.DingBotMsgCallbackRequest;
import com.mavin.dingtalk.service.callback.bot.IDingBotMsgCallBackHandler;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Mavin
 * @date 2024/7/6 17:41
 * @description 默认钉钉机器人回调消息HTTP模式控制器
 */
@Slf4j
@RestController
@RequestMapping("/ding")
@ConditionalOnProperty(prefix = "dingtalk.miniH5.bot", name = "httpEnabled", havingValue = "true")
public class DingBotMsgCallbackHttpController extends AbstractDingBotMsgCallbackController<DingBotMsgCallbackRequest> {

    public DingBotMsgCallbackHttpController(
            List<IDingBotMsgCallBackHandler<DingBotMsgCallbackRequest>> dingBotMsgCallBackHandlers
    ) {
        super(dingBotMsgCallBackHandlers);
    }

    @SneakyThrows
    @RequestMapping("/bot/msg/callback")
    public void msg(
            @RequestHeader(value = "timestamp", required = false) String timeStamp,
            @RequestHeader(value = "sign") String sign,
            @RequestBody DingBotMsgCallbackRequest payload
    ) {
        log.debug("timestamp:{},sign:{},content:{}", timeStamp, sign, new Gson().toJson(payload));
        IDingMiniH5 app = DingAppFactory.getMiniH5AppByRobotCode(payload.getRobotCode());
        Preconditions.checkNotNull(
                app, String.format("SpringContext中未找到对应注册了机器人的钉钉应用Bean: RobotCode:%s", payload.getRobotCode())
        );
        validateRequest(timeStamp, sign, app);
        handlers(app, payload).ifPresent(
                handlers -> handlers.parallelStream().forEachOrdered(
                        handler -> {
                            if (!handler.handle(app, payload)) {
                                log.warn("机器人回调处理失败，handler:{}", handler.description());
                            }
                        }
                )
        );
    }

}
