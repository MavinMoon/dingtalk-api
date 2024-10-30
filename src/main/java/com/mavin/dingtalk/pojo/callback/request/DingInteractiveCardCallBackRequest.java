package com.mavin.dingtalk.pojo.callback.request;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 用于封装钉钉互动卡片的回调HTTP请求体对象
 *
 * @author Mavin
 */
@Getter
@Setter
public class DingInteractiveCardCallBackRequest {

    private String outTrackId;
    private String corpId;
    private String userId;
    private String content;

    public Content getContent() {
        return new Gson().fromJson(content, new TypeToken<Content>() {
        }.getType());
    }

    @Getter
    @Setter
    public static class Content {
        private CardPrivateData cardPrivateData;
    }

    @Setter
    @Getter
    public static class CardPrivateData {

        private List<String> actionIds;
        private JsonObject params;

        public <T> T getParams(Class<T> tClass) {
            final Gson gson = new Gson();
            return gson.fromJson(gson.toJson(params), tClass);
        }
    }


    @Override
    public String toString() {
        return "DingInteractiveCardCallBackRequest{" + "outTrackId='" + outTrackId + '\'' +
                ", getCorpId='" + corpId + '\'' +
                ", userId='" + userId + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
