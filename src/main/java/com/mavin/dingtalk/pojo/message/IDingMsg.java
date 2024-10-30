package com.mavin.dingtalk.pojo.message;

/**
 * @author Mavin
 * @date 2024/6/27 10:37
 * @description DingTalk message design according to the documentation.
 */
public interface IDingMsg<T> {

    /**
     * Based on the DingTalk documentation, the actual request body usually has a field called msgKey,
     * but this field is implemented poorly in many APIs due to the different formats it has.
     * <p>
     * Implementation of this interface should consider overriding this method.
     *
     * @return the literal key of the concept msgKey
     */
    String getMsgKey();

    /**
     * A message formatted as requested by the API according to the documentation.
     * Based on the DingTalk documentation, the message is usually converted into a JSON String.
     * <p>
     * Implementation of this interface should override this method.
     *
     * @return formatted message
     */
    T getMsg();

}
