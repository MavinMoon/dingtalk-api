package com.mavin.dingtalk.pojo.callback.request;

import lombok.Data;

/**
 * 用于封装钉钉微应用H5的事件回调HTTP请求体对象
 *
 * @author Mavin
 */
@Data
public class DingMiniH5EventCallbackRequest {

    private String msg_signature;
    private String timestamp;
    private String nonce;

}
