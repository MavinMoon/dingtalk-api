package com.mavin.dingtalk.component.factory.token;

import com.mavin.dingtalk.component.IDingApi;
import com.mavin.dingtalk.component.application.IDingApp;

import java.util.Optional;

/**
 * @author Mavin
 * @date 2024/6/24 14:59
 * @description 钉钉tokenAPI
 */
public interface IDingToken extends IDingApi {

    /**
     * 获取企业内部应用的accessToken新版SDK
     * @param app 企业内部应用
     * @return token
     */
    Optional<String> getAccessTokenWithNew(IDingApp app);

    /**
     * 获取企业内部应用的accessToken旧版SDK
     * @param app 企业内部应用
     * @return token
     */
    Optional<String> getAccessTokenWithOld(IDingApp app);


}
