package com.mavin.dingtalk.service.user;

import com.dingtalk.api.request.OapiV2UserGetRequest;
import com.dingtalk.api.request.OapiV2UserGetuserinfoRequest;
import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.dingtalk.api.response.OapiV2UserGetuserinfoResponse;
import com.mavin.dingtalk.component.application.IDingMiniH5;
import com.mavin.dingtalk.constant.Language;
import com.mavin.dingtalk.service.AbstractDingApiHandler;
import com.mavin.dingtalk.service.IDingUserHandler;
import com.mavin.dingtalk.constant.DingUrlConstant;
import lombok.NonNull;

/**
 * @author Mavin
 * @date 2024/6/25 17:03
 * @description 钉钉用户服务
 */
public class DingUserHandler extends AbstractDingApiHandler implements IDingUserHandler {

    public DingUserHandler(@NonNull IDingMiniH5 app) {
        super(app);
    }

    @Override
    public OapiV2UserGetuserinfoResponse.UserGetByCodeResponse getUserByCode(@NonNull String code) {
        OapiV2UserGetuserinfoRequest request = new OapiV2UserGetuserinfoRequest();
        request.setCode(code);
        return execute(DingUrlConstant.User.GET_USER_BY_LOGIN_AUTH_CODE, request, () -> "根据免登码获取用户信息").getResult();
    }

    @Override
    public OapiV2UserGetResponse.UserGetResponse getUserByUserId(@NonNull String userId) {
        OapiV2UserGetRequest request = new OapiV2UserGetRequest();
        request.setUserid(userId);
        request.setLanguage(Language.zh_CN.name());
        return execute(DingUrlConstant.User.GET_USER_BY_USER_ID, request, () -> "根据钉钉userId获取user").getResult();
    }

}
