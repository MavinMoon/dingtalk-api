package com.mavin.dingtalk.pojo.message.interactive.callback;

import com.mavin.dingtalk.component.application.IDingMiniH5;
import com.mavin.dingtalk.component.DingBooter;

import java.util.List;

/**
 * Configure and register an instance of this interface
 * to the Spring Container before sending any interactive
 * card messages that need to interact with users.
 * <p>
 * Note:
 * <p>
 * Any implementations of this interface only need to be
 * registered once, so in this framework, the registration process
 * was done at the ready phase of the SpringBoot application boot-up process.
 * See: {@link DingBooter}
 *
 * @author Mavin
 */
public interface IDingInteractiveCardCallBack {

    /**
     * When users interact with messages, DingTalk will send POST requests to the URL.
     *
     * @return callback url
     */
    String getCallbackUrl();

    /**
     * The key is used to send with the interactive card message.
     *
     * @return route key
     */
    String getCallbackRouteKey();

    /**
     * The api secret is used to sign the request.
     *
     * @return api secret
     */
    default String getApiSecret(){
        return null;
    }

    /**
     * MiniH5 applications were used to send the interactive card message.
     *
     * @return MiniH5 application instances
     */
    List<IDingMiniH5> getDingMiniH5Apps();
}
