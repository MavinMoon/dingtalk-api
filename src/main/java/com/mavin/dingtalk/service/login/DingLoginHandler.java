package com.mavin.dingtalk.service.login;

import com.aliyun.dingtalkcontact_1_0.models.GetUserHeaders;
import com.aliyun.dingtalkcontact_1_0.models.GetUserResponseBody;
import com.aliyun.dingtalkoauth2_1_0.models.GetUserTokenRequest;
import com.aliyun.dingtalkoauth2_1_0.models.GetUserTokenResponseBody;
import com.aliyun.teautil.models.RuntimeOptions;
import com.dingtalk.api.request.OapiUserGetbyunionidRequest;
import com.dingtalk.api.response.OapiUserGetbyunionidResponse;
import com.mavin.dingtalk.component.application.IDingMiniH5;
import com.mavin.dingtalk.component.exception.DingApiException;
import com.mavin.dingtalk.service.AbstractDingApiHandler;
import com.mavin.dingtalk.service.IDingLoginHandler;
import com.mavin.dingtalk.constant.DingUrlConstant;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Mavin
 * @date 2024/6/25 16:11
 * @description 钉钉登录服务
 */
@Slf4j
public class DingLoginHandler extends AbstractDingApiHandler implements IDingLoginHandler {

    public DingLoginHandler(@NonNull IDingMiniH5 app) {
        super(app);
    }

    @SneakyThrows(Exception.class)
    @Override
    public GetUserTokenResponseBody getUserToken(@NonNull String content, @NonNull GrantType grantType) {
        GetUserTokenRequest getUserTokenRequest = new GetUserTokenRequest()
                .setClientId(app.getClientId())
                .setClientSecret(app.getClientSecret())
                .setGrantType(grantType.name());
        if (GrantType.authorization_code.equals(grantType)) {
            getUserTokenRequest.setCode(content);
        } else if (GrantType.refresh_token.equals(grantType)) {
            getUserTokenRequest.setRefreshToken(content);
        } else {
            throw new DingApiException("不支持的授权类型");
        }
        return execute(
                com.aliyun.dingtalkoauth2_1_0.Client.class,
                client -> client.getUserToken(getUserTokenRequest).getBody(),
                () -> "获取用户token"
        );
    }

    @Override
    public GetUserResponseBody getUserWithOptions(@NonNull String accessToken, @NonNull String unionId) {
        GetUserHeaders getUserHeaders = new GetUserHeaders();
        getUserHeaders.setXAcsDingtalkAccessToken(accessToken);
        return execute(
                com.aliyun.dingtalkcontact_1_0.Client.class,
                client -> client.getUserWithOptions(unionId, getUserHeaders, new RuntimeOptions()).getBody(),
                () -> "获取用户通讯录个人信息"
        );
    }

    @Override
    public OapiUserGetbyunionidResponse.UserGetByUnionIdResponse getUserIdByUnionId(@NonNull String unionId) {
        OapiUserGetbyunionidRequest request = new OapiUserGetbyunionidRequest();
        request.setUnionid(unionId);
        return execute(DingUrlConstant.User.GET_USER_BY_LOGIN_AUTH_CODE_NEW, request, () -> "根据unionid获取用户userid").getResult();
    }


}
