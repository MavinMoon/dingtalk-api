package com.mavin.dingtalk.controller;

import com.mavin.dingtalk.component.exception.DingApiException;
import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author Mavin
 * @date 2024/7/2 18:23
 * @description 钉钉互动卡片回调控制器基类
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractDingInteractiveMessageCallbackController {

    /**
     * 计算签名
     *
     * @param apiSecret apiSecret
     * @param ts        时间戳
     * @return 签名
     */
    protected String calcSignature(String apiSecret, long ts) {
        Preconditions.checkNotNull(apiSecret, "apiSecret must not be null");
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec key = new SecretKeySpec(apiSecret.getBytes(), "HmacSHA256");
            mac.init(key);
            return Base64.getEncoder().encodeToString(mac.doFinal(Long.toString(ts).getBytes()));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new DingApiException("sign api secret failed", e);
        }
    }

}
