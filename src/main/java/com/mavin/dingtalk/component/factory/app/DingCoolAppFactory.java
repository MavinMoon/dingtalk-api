package com.mavin.dingtalk.component.factory.app;

import com.mavin.dingtalk.component.application.IDingCoolApp;
import com.mavin.dingtalk.component.application.IDingMiniH5;
import com.google.common.base.Preconditions;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Mavin
 * @date 2024/6/28 10:24
 * @description 钉钉酷应用工厂，暂仅供客户端使用
 */
@Slf4j
@UtilityClass
public class DingCoolAppFactory {

    /**
     * 钉钉应用缓存，KEY：COOL_CODE，VALUE：DING_COOL_APP
     */
    private static final Map<String, IDingCoolApp> KEY_CACHE = new HashMap<>(8);

    /**
     * 钉钉应用缓存，KEY：COOL_CODE，VALUE：DING_MINI_H5_APP
     */
    private static final Map<String, IDingMiniH5> KEY_MINI_H5_CACHE = new HashMap<>(8);


    public static void setAppCache(IDingCoolApp app) {
        Preconditions.checkArgument(Objects.nonNull(app), "钉钉酷应用不能为空");
        KEY_CACHE.put(app.getCoolAppCode(), app);
        KEY_MINI_H5_CACHE.put(app.getCoolAppCode(), app.getMiniH5());
    }

    public static IDingCoolApp app(String key) {
        return KEY_CACHE.get(key);
    }

    public static IDingMiniH5 miniH5(String key) {
        return KEY_MINI_H5_CACHE.get(key);
    }

}
