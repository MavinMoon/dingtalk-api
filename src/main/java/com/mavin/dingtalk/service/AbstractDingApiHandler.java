package com.mavin.dingtalk.service;

import com.aliyun.tea.TeaException;
import com.aliyun.tea.TeaModel;
import com.aliyun.teaopenapi.Client;
import com.aliyun.teaopenapi.models.Config;
import com.dingtalk.api.DefaultDingTalkClient;
import com.mavin.dingtalk.annotation.MethodDesc;
import com.mavin.dingtalk.component.IDingNewApi;
import com.mavin.dingtalk.component.IDingOldApi;
import com.mavin.dingtalk.component.SDKVersion;
import com.mavin.dingtalk.component.application.IDingApp;
import com.mavin.dingtalk.component.exception.DingApiException;
import com.mavin.dingtalk.component.factory.token.DingAccessTokenFactory;
import com.mavin.dingtalk.utils.DingUtils;
import com.mavin.dingtalk.utils.ThrowingFunction;
import com.taobao.api.ApiException;
import com.taobao.api.TaobaoRequest;
import com.taobao.api.TaobaoResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.lang.reflect.Constructor;
import java.util.function.Supplier;

/**
 * @author Mavin
 * @date 2024/6/25 10:52
 * @description
 */
@Slf4j
public abstract class AbstractDingApiHandler implements IDingOldApi, IDingNewApi {

    protected final IDingApp app;

    protected AbstractDingApiHandler(@NonNull IDingApp app) {
        this.app = app;
    }

    @MethodDesc("执行旧版钉钉接口请求")
    protected <T extends TaobaoResponse> T execute(
            @NonNull String url,
            @NonNull TaobaoRequest<T> request,
            Supplier<String> description
    ) {
        return execute(url, request, true, description);
    }

    @MethodDesc("执行旧版钉钉接口请求")
    protected <T extends TaobaoResponse> T execute(
            @NonNull String url,
            @NonNull TaobaoRequest<T> request,
            boolean usingAccessToken,
            Supplier<String> description
    ) {
        DefaultDingTalkClient client = new DefaultDingTalkClient(url);
        final T response;
        try {
            if (usingAccessToken) {
                String accessToken = getAccessToken(SDKVersion.OLD);
                response = client.execute(request, accessToken);
            } else {
                response = client.execute(request, app.getClientId(), app.getClientSecret());
            }
            // 错误码校验
            DingUtils.isSuccessWithOldApi(response);
        } catch (ApiException err) {
            final String errMsg = err.getErrMsg();
            final String errCode = err.getErrCode();
            final String subErrMsg = err.getSubErrMsg();
            final String subErrCode = err.getSubErrCode();
            final String desc = description.get();
            log.error("{}旧版SDK响应失败, errCode:{}, errMsg:{}, subErrCode:{}, subErrMsg:{}", desc, errCode, errMsg,
                    subErrCode, subErrMsg);
            throw new DingApiException(String.format("%s旧版SDK响应失败, errCode:%s, errMsg:%s, subErrCode:%s,subErrMsg:%s"
                    , desc, errCode, errMsg, subErrCode, subErrMsg), err);
        }
        return response;
    }

    @MethodDesc("执行新版钉钉接口请求")
    protected <CLIENT extends Client, RESPONSE extends TeaModel> RESPONSE execute(
            Class<CLIENT> clazz, ThrowingFunction<CLIENT, RESPONSE, Exception> function, Supplier<String> description
    ) {
        try {
            final Constructor<CLIENT> constructor = clazz.getConstructor(Config.class);
            CLIENT client = constructor.newInstance(getConfig());
            return function.apply(client);
        } catch (TeaException err) {
            if (StringUtils.hasText(err.code) && StringUtils.hasText(err.message)) {
                String desc = description.get();
                log.error("{}执行新版SDK失败，code：{}，errMsg:{}", desc, err.code, err.message, err);
                throw new DingApiException(String.format("%s执行新版SDK失败，code：%s，errMsg:%s", desc, err.code, err.message), err);
            }
            throw new DingApiException("执行新版SDK失败", err);
        } catch (Exception e) {
            String desc = description.get();
            TeaException err = new TeaException(e.getMessage(), e);
            if (StringUtils.hasText(err.message)) {
                log.error("{}执行新版SDK失败，code：{}，errMsg:{}", desc, err.code, err.message, err);
                throw new DingApiException(String.format("%s执行新版SDK失败，code：%s，errMsg:%s", desc, err.code, err.message), err);
            }
            throw new DingApiException("执行新版SDK失败", err);
        }
    }

    protected String getAccessToken(SDKVersion version) {
        return DingAccessTokenFactory.getAccessToken(app, version);
    }

}
