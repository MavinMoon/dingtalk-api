package com.mavin.dingtalk.service;

import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.dingtalk.api.response.OapiV2UserGetuserinfoResponse;
import lombok.NonNull;

/**
 * @author Mavin
 * @date 2024/6/25 17:00
 * @description 钉钉用户信息接口
 */
public interface IDingUserHandler {

    /**
     * 根据钉钉免登码获取钉钉用户信息json字符串
     *
     * @param code 钉钉免登码
     * @return 钉钉用户信息
     */
    OapiV2UserGetuserinfoResponse.UserGetByCodeResponse getUserByCode(@NonNull String code);

    /**
     * 根据钉钉userId获取钉钉用户信息
     *
     * @param userId 钉钉userId
     * @return 钉钉用户信息
     */
    OapiV2UserGetResponse.UserGetResponse getUserByUserId(@NonNull String userId);

}
