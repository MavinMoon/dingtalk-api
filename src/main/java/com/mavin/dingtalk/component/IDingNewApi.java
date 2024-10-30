package com.mavin.dingtalk.component;

import com.aliyun.teaopenapi.models.Config;
import com.mavin.dingtalk.annotation.MethodDesc;

/**
 * @author Mavin
 * @date 2024/6/24 14:24
 * @description 钉钉新版接口
 */
public interface IDingNewApi extends IDingApi {

    SDKVersion NEW = SDKVersion.NEW;

    /**
     * 新版SDK的client配置
     *
     * @return Config
     */
    @MethodDesc("新版SDK的client配置")
    default Config getConfig() {
        Config config = new Config();
        config.protocol = "https";
        config.regionId = "central";
        return config;
    }

}
