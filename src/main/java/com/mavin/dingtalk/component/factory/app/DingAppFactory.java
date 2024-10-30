package com.mavin.dingtalk.component.factory.app;

import com.mavin.dingtalk.component.application.IDingApp;
import com.mavin.dingtalk.component.application.IDingMiniH5;
import com.google.common.base.Preconditions;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 钉钉应用工厂
 * 既然已经使用了spring框架，直接使用其容器完成单例效果
 * 实现IDingApp接口并注册到spring容器中的类将被自动添加到工厂缓存中
 *
 * @author Mavin
 */
@Slf4j
@UtilityClass
public class DingAppFactory {

    /**
     * 钉钉应用缓存，KEY：CLIENT_ID，VALUE：DING_APP
     */
    private static final Map<String, IDingApp> KEY_CACHE = new HashMap<>(8);

    /**
     * 钉钉应用缓存，KEY：APP_ID，VALUE：DING_APP
     */
    private static final Map<String, IDingApp> ID_CACHE = new HashMap<>(8);

    public static void setAppCache(IDingApp app) {
        Preconditions.checkArgument(Objects.nonNull(app), "钉钉应用不能为空");
        KEY_CACHE.put(app.getClientId(), app);
        ID_CACHE.put(app.getAppId(), app);
    }

    public static IDingMiniH5 getMiniH5AppByRobotCode(@NonNull String robotCode) {
        return KEY_CACHE.values().stream()
                .filter(IDingMiniH5.class::isInstance)
                .map(IDingMiniH5.class::cast)
                .filter(dingMiniH5 -> robotCode.equals(dingMiniH5.getRobotCode()))
                .findFirst().orElse(null);
    }

    public static <T extends IDingApp> T getAppByClazz(Class<T> clazz) {
        return CollectionUtils.findValueOfType(KEY_CACHE.values(), clazz);
    }

    public static <T extends IDingApp> T getAppByAppId(String appId) {
        return (T) ID_CACHE.get(appId);
    }

    public static <T extends IDingApp> T getAppByClientId(String clientId) {
        return (T) KEY_CACHE.get(clientId);
    }



}
