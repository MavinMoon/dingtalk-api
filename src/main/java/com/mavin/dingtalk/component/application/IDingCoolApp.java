package com.mavin.dingtalk.component.application;

import com.mavin.dingtalk.annotation.MethodDesc;
import com.mavin.dingtalk.component.factory.app.DingCoolAppFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;

/**
 * @author Mavin
 * @date 2024/6/24 14:52
 * @description 钉钉酷应用定义，相当于微应用内部的一个配置项，暂无作用
 */
public interface IDingCoolApp extends SmartInitializingSingleton {

    /**
     * 酷应用编码
     */
    @MethodDesc(value = "酷应用编码")
    String getCoolAppCode();

    @MethodDesc(value = "钉钉应用")
    IDingMiniH5 getMiniH5();

    @Override
    default void afterSingletonsInstantiated() {
        DingCoolAppFactory.setAppCache(this);
    }

}
