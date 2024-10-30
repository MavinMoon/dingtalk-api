package com.mavin.dingtalk.service;

import com.aliyun.dingtalkcontact_1_0.models.GetUserResponseBody;
import com.aliyun.dingtalkoauth2_1_0.models.GetUserTokenResponseBody;
import com.dingtalk.api.response.OapiUserGetbyunionidResponse;
import com.mavin.dingtalk.annotation.MethodDesc;
import com.mavin.dingtalk.component.exception.DingApiException;
import lombok.NonNull;

/**
 * @author Mavin
 * @date 2024/6/25 10:51
 * @description 钉钉登录相关接口
 */
public interface IDingLoginHandler {

    enum GrantType {
        /**
         * 如果使用授权码换token，传authorization_code
         */
        authorization_code,
        /**
         * 如果使用刷新token换用户token，传refresh_token
         */
        refresh_token
    }

    /**
     * 登录时通过钉钉回调获取的授权码换取用户token，常用在登录
     *
     * @param content   内容
     * @param grantType 授权码类型
     * @return userToken
     * @throws DingApiException 钉钉接口异常
     */
    @MethodDesc("登录时通过钉钉回调获取的授权码换取用户token，grantType为authorization_code时传authCode，为refresh_token时传refreshToken")
    GetUserTokenResponseBody getUserToken(@NonNull String content, @NonNull GrantType grantType);

    /**
     * 获取用户通讯录个人信息新版SDK
     *
     * @param accessToken 钉钉个人用户的accessToken
     * @param unionId     用户的unionId，如需获取当前授权人的信息，unionId参数可以传me。
     * @return GetUserResponseBody
     * @throws DingApiException 钉钉接口异常
     */
    @MethodDesc("获取用户通讯录个人信息，accessToken为钉钉个人用户的accessToken，unionId如获取当前授权人则传me")
    GetUserResponseBody getUserWithOptions(@NonNull String accessToken, @NonNull String unionId);

    /**
     * 根据unionid获取用户userid
     *
     * @param unionId 唯一钉钉ID
     * @return 结果
     * @throws DingApiException 钉钉接口异常
     */
    @MethodDesc("根据unionid获取用户userid")
    OapiUserGetbyunionidResponse.UserGetByUnionIdResponse getUserIdByUnionId(@NonNull String unionId);

}
