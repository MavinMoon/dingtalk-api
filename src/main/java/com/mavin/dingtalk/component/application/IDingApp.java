package com.mavin.dingtalk.component.application;

import com.mavin.dingtalk.annotation.MethodDesc;
import com.mavin.dingtalk.component.factory.app.DingAppFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author Mavin
 * @date 2024/6/24 14:31
 * @description 钉钉应用，整个项目基于Spring容器能力，所以最好将APP实现注册到IoC容器中
 */
public interface IDingApp extends IDingCorp, InitializingBean {

    /**
     * @return 钉钉应用key
     */
    @MethodDesc(value = "钉钉应用key(原 AppKey 和 SuiteKey)")
    String getClientId();

    /**
     * @return 钉钉应用密钥
     */
    @MethodDesc(value = "钉钉应用密钥(原 AppSecret 和 SuiteSecret)")
    String getClientSecret();

    /**
     * @return 钉钉应用名称
     */
    @MethodDesc(value = "钉钉应用名称")
    String getAppName();

    /**
     * @return 钉钉应用唯一标识
     */
    @MethodDesc(value = "钉钉应用唯一标识")
    String getAppId();

    @Override
    default void afterPropertiesSet() {
        DingAppFactory.setAppCache(this);
    }

}
