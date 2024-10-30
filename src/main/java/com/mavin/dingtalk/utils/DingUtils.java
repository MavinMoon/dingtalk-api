package com.mavin.dingtalk.utils;

import com.google.gson.GsonBuilder;
import com.taobao.api.ApiException;
import com.taobao.api.TaobaoResponse;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author Mavin
 * @date 2024/6/24 11:29
 * @description 钉钉工具类
 */
@Slf4j
@UtilityClass
public class DingUtils {

    public static void isSuccessWithOldApi(TaobaoResponse response) throws ApiException {
        isTrueWithOldApi(response.isSuccess(), response);
        /* 全局错误码：https://open.dingtalk.com/document/orgapp/server-api-error-codes-1 */
        isTrueWithOldApi(Objects.equals("0", response.getErrorCode()), response);
    }

    private static void isTrueWithOldApi(boolean expression, TaobaoResponse response) throws ApiException {
        if (!expression) {
            log.error("钉钉请求异常:{}", new GsonBuilder().create().toJson(response));
            throw new ApiException(response.getErrorCode(), response.getMsg(), response.getSubCode(), response.getSubMsg());
        }
    }

    public static String getDingTalkSkipUrl(String url) {
        return "dingtalk://dingtalkclient/page/link?url=" + url;
    }

}
