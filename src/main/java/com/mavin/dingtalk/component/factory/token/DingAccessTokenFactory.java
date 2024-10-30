package com.mavin.dingtalk.component.factory.token;

import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenRequest;
import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenResponse;
import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenResponseBody;
import com.aliyun.tea.TeaException;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.mavin.dingtalk.annotation.FieldDesc;
import com.mavin.dingtalk.annotation.MethodDesc;
import com.mavin.dingtalk.component.IDingNewApi;
import com.mavin.dingtalk.component.IDingOldApi;
import com.mavin.dingtalk.component.SDKVersion;
import com.mavin.dingtalk.component.application.IDingApp;
import com.mavin.dingtalk.component.exception.DingApiException;
import com.mavin.dingtalk.utils.DingUtils;
import com.google.common.base.Preconditions;
import com.mavin.dingtalk.constant.DingUrlConstant;
import com.taobao.api.ApiException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mavin
 * @date 2024/6/24 15:03
 * @description 钉钉访问凭证工厂
 */
@Slf4j
public class DingAccessTokenFactory implements IDingToken, IDingOldApi, IDingNewApi {


    private DingAccessTokenFactory() {
    }

    private static class SingletonHolder {
        private static final DingAccessTokenFactory INSTANCE = new DingAccessTokenFactory();
    }

    /**
     * TOKEN缓存，KEY：CLIENT_ID
     */
    private static final ConcurrentHashMap<String, ConcurrentHashMap<SDKVersion, DingToken>> TOKEN_CACHE = new ConcurrentHashMap<>(16);

    public static String getAccessToken(IDingApp dingApp, SDKVersion version) {
        log.debug("Getting accessToken with:{},usage: new sdk APIs", dingApp.getAppName());
        final DingAccessTokenFactory bean = SingletonHolder.INSTANCE;
        if (Objects.equals(version, SDKVersion.NEW)) {
            return bean.getAccessTokenWithNew(dingApp).orElseThrow(() -> new DingApiException("获取新版AccessToken失败"));
        }
        return bean.getAccessTokenWithOld(dingApp).orElseThrow(() -> new DingApiException("获取旧版AccessToken失败"));
    }


    @SneakyThrows
    @Override
    public Optional<String> getAccessTokenWithNew(IDingApp app) {
        final String clientId = app.getClientId();
        final String clientSecret = app.getClientSecret();
        Optional<String> token = checkCache(clientId, NEW);
        if (token.isPresent()) {
            return token;
        }
        com.aliyun.dingtalkoauth2_1_0.Client client = new com.aliyun.dingtalkoauth2_1_0.Client(getConfig());
        GetAccessTokenRequest getAccessTokenRequest = new GetAccessTokenRequest()
                .setAppKey(clientId)
                .setAppSecret(clientSecret);
        try {
            GetAccessTokenResponse accessTokenResponse = client.getAccessToken(getAccessTokenRequest);
            final GetAccessTokenResponseBody body = accessTokenResponse.getBody();
            final String accessToken = body.getAccessToken();
            final Long expireIn = body.getExpireIn();
            log.debug("获取企业内部应用的accessToken:{},app:{},ttl:{}", accessToken, app.getAppName(), expireIn);
            storeCache(clientId, accessToken, expireIn, NEW);
            return Optional.of(accessToken);
        } catch (TeaException err) {
            if (StringUtils.hasText(err.code) && StringUtils.hasText(err.message)) {
                log.error("获取企业内部应用的accessToken,app:{},err:{}:{}", app.getAppName(), err.code, err.message);
            }
            throw new DingApiException(err.message, err);
        } catch (Exception e) {
            TeaException err = new TeaException(e.getMessage(), e);
            if (StringUtils.hasText(err.code) && StringUtils.hasText(err.message)) {
                log.error("获取企业内部应用的accessToken,app:{},err:{}:{}", app.getAppName(), err.code, err.message);
            }
            throw new DingApiException(err.message, e);
        }
    }

    @Override
    public Optional<String> getAccessTokenWithOld(IDingApp app) {
        final String clientId = app.getClientId();
        final String clientSecret = app.getClientSecret();
        Optional<String> token = checkCache(clientId, OLD);
        if (token.isPresent()) {
            return token;
        }
        DefaultDingTalkClient client = new DefaultDingTalkClient(DingUrlConstant.AccessToken.ACCESS_TOKEN_OLD);
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(clientId);
        request.setAppsecret(clientSecret);
        request.setHttpMethod("GET");
        try {
            OapiGettokenResponse response = client.execute(request);
            DingUtils.isSuccessWithOldApi(response);
            String accessToken = response.getAccessToken();
            Long expiresIn = response.getExpiresIn();
            log.debug("获取企业内部应用的access_token:{},app:{},ttl:{}", accessToken, app.getAppName(), expiresIn);
            storeCache(clientId, accessToken, expiresIn, OLD);
            return Optional.of(accessToken);
        } catch (ApiException e) {
            log.error("获取企业内部应用的access_token旧版SDK异常,应用:{}", app.getAppName(), e);
            throw new DingApiException("获取企业内部应用的access_token异常", e);
        }
    }

    /**
     * 检查TOKEN缓存是否存在
     *
     * @param clientId 已创建的企业内部应用的ClientId
     * @param version  sdk版本
     * @return TOKEN
     */
    private Optional<String> checkCache(String clientId, SDKVersion version) {
        final ConcurrentHashMap<SDKVersion, DingToken> map = TOKEN_CACHE.get(clientId);
        if (CollectionUtils.isEmpty(map) || map.get(version) == null) {
            return Optional.empty();
        }
        final DingToken token = map.get(version);
        if (token.isExpired()) {
            TOKEN_CACHE.remove(clientId);
            return Optional.empty();
        }
        return Optional.ofNullable(token.getAccessToken());
    }

    /**
     * 存储钉钉访问TOKEN
     *
     * @param clientId    已创建的企业内部应用的ClientId
     * @param accessToken 钉钉访问TOKEN
     * @param expireIn    过期时间（秒）
     * @param version     sdk版本
     */
    @MethodDesc(value = "缓存钉钉访问TOKEN，为避免请求过程中的访问中断，小于10分钟以内视为Token已过期不进行存储")
    private void storeCache(String clientId, String accessToken, Long expireIn, SDKVersion version) {
        if (!StringUtils.hasText(accessToken)) {
            log.debug("钉钉AccessToken:{}不存在,clientId:{},SDKVersion:{}", accessToken, clientId, version.name());
            return;
        }
        Preconditions.checkArgument(expireIn != null && expireIn > 600L, "ACCESS_TOKEN EXPIRED");
        ConcurrentHashMap<SDKVersion, DingToken> map = TOKEN_CACHE.get(clientId);
        if (CollectionUtils.isEmpty(map)) {
            map = new ConcurrentHashMap<>(1);
        }
        map.put(version, new DingToken(accessToken, version, LocalDateTime.now().plusSeconds(expireIn - 600)));
        TOKEN_CACHE.put(clientId, map);
    }

    @Slf4j
    @Getter
    @Setter
    @AllArgsConstructor
    public static class DingToken {

        @FieldDesc(value = "钉钉access_token")
        private String accessToken;

        @FieldDesc(value = "钉钉SDK版本")
        private SDKVersion version;

        @FieldDesc(value = "钉钉access_token过期时间")
        private LocalDateTime expiredAt;

        public boolean isExpired() {
            log.debug("钉钉AccessToken:{},SDK.version:{},到期时间:{}", accessToken, version.name(), getExpiredAt());
            return !this.expiredAt.isAfter(LocalDateTime.now());
        }
    }
}
