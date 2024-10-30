package com.mavin.dingtalk.pojo.message.interactive;

import com.mavin.dingtalk.pojo.message.IDingMsg;
import org.springframework.cglib.beans.BeanMap;

import java.util.*;

/**
 * @author Mavin
 * @date 2024/6/28 14:37
 * @description 钉钉互动卡片消息
 */
public interface IDingInteractiveMsg extends IDingMsg<Map<String, String>> {

    /**
     * Ignore card content fields
     *
     * @return A list of fields to be ignored
     */
    default List<String> getIgnoreFields() {
        return Arrays.asList("outTrackId", "cardTemplateId", "callbackRouteKey", "privateData");
    }

    /**
     * Empty implementation
     * <p>
     * Should not be called.
     *
     * @return null
     */
    @Override
    default String getMsgKey() {
        throw new IllegalStateException("The interactive card message doesn't need a msgKey");
    }


    /**
     * According to the documentation and the APIs, convert the
     * message object to a map.
     *
     * @return A map of which keys are the literal fields' names,
     * and values are the fields' values converted into strings.
     */
    @Override
    default Map<String, String> getMsg() {
        final BeanMap beanMap = BeanMap.create(this);
        final Map<String, String> map = new HashMap<>(beanMap.size());
        beanMap.forEach((k, v) -> {
            if (Objects.nonNull(v) && !getIgnoreFields().contains(String.valueOf(k))) {
                map.put(String.valueOf(k), String.valueOf(v));
            }
        });
        return map;
    }

    /**
     * Used as the identification of the card
     *
     * @return id sequence
     */
    String getOutTrackId();

    /**
     * The callback route key, directly related to the interactive card message,
     * should be configured before constructing an interactive card message.
     *
     * @return the route key.
     */
    String getCallbackRouteKey();

    /**
     * After constructing an interactive card message template
     * on the card platform, the card template Id can be found in the template description
     * section.
     *
     * @return the card template id of the template
     */
    String getCardTemplateId();

    /**
     * The private data of the card.
     *
     * @return the private data of the card
     */
    Map<String, Map<String, String>> getPrivateData();

}
