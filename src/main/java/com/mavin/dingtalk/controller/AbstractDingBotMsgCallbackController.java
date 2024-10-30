package com.mavin.dingtalk.controller;

import cn.hutool.core.text.CharSequenceUtil;
import com.mavin.dingtalk.annotation.MethodDesc;
import com.mavin.dingtalk.component.application.IDingMiniH5;
import com.mavin.dingtalk.service.callback.bot.IDingBotMsgCallBackHandler;
import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Mavin
 * @date 2024/7/6 17:27
 * @description 钉钉机器人消息回调控制器基类
 */
@RequiredArgsConstructor
public abstract class AbstractDingBotMsgCallbackController<PAYLOAD> {

    protected final List<IDingBotMsgCallBackHandler<PAYLOAD>> dingBotMsgCallBackHandlers;

    @MethodDesc("校验Http请求")
    protected static void validateRequest(String timeStamp, String sign, IDingMiniH5 app) throws NoSuchAlgorithmException, InvalidKeyException {
        Preconditions.checkArgument(System.currentTimeMillis() - Long.parseLong(timeStamp) <= 60 * 60 * 1000, "非法钉钉机器人回调请求");
        Preconditions.checkArgument(Objects.equals(getSign(timeStamp, app), sign), "非法钉钉机器人回调请求");
    }

    private static String getSign(String timeStamp, IDingMiniH5 app) throws NoSuchAlgorithmException, InvalidKeyException {
        Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(timeStamp));
        Preconditions.checkNotNull(app);
        String calculatedSign = timeStamp + "\n" + app.getClientSecret();
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(app.getClientSecret().getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] signData = mac.doFinal(calculatedSign.getBytes(StandardCharsets.UTF_8));
        return new String(Base64.encodeBase64(signData));
    }

    /**
     * 获取处理器列表
     *
     * @param app     机器人应用
     * @param payload 机器人消息回调请求体
     * @return 处理器集合
     */
    protected Optional<List<IDingBotMsgCallBackHandler<PAYLOAD>>> handlers(IDingMiniH5 app, PAYLOAD payload) {
        if (payload == null) {
            return Optional.empty();
        }
        final Optional<IDingBotMsgCallBackHandler<PAYLOAD>> first = dingBotMsgCallBackHandlers.stream()
                .sorted(Comparator.comparing(IDingBotMsgCallBackHandler::predicateOrder))
                .filter(handler -> handler.predication() != null && handler.predication().test(payload))
                .filter(handler -> !handler.ignoredApps().contains(app.getClass()))
                .findFirst();
        return first.map(handler -> Optional.of(Collections.singletonList(handler)))
                .orElseGet(() -> Optional.of(dingBotMsgCallBackHandlers.stream()
                        .filter(handler -> handler.predication() == null)
                        .collect(Collectors.toList()))
                );
    }


}
