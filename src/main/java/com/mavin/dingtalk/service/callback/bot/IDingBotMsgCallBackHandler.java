package com.mavin.dingtalk.service.callback.bot;

import com.mavin.dingtalk.annotation.MethodDesc;
import com.mavin.dingtalk.component.application.IDingMiniH5;

import java.util.Collections;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @author Mavin
 * @date 2024/7/6 14:33
 * @description 钉钉机器人自动回复处理器定义
 */
public interface IDingBotMsgCallBackHandler<PAYLOAD> {


    /**
     * 是否可以执行（用以区分机器人的不同处理能力）
     *
     * @return Predicate
     */
    @MethodDesc("断言判断是否可以执行，用以区分机器人的不同处理能力")
    Predicate<PAYLOAD> predication();

    /**
     * 表达式遍历顺序
     * <p>
     * 注意：是pattern在遍历匹配正则的顺序，而非处理器执行顺序
     * <p>
     * 该值仅在Pattern不为空时作为匹配顺序进行排序
     *
     * @return 排序值
     */
    default Integer predicateOrder() {
        return 0;
    }

    /**
     * 处理器功能说明
     *
     * @return string
     */
    default String description() {
        return "";
    }

    /**
     * 处理入口
     *
     * @param app     钉钉H5微应用
     * @param payload 机器人消息回调请求体
     * @return 是否处理成功
     */
    boolean handle(IDingMiniH5 app, PAYLOAD payload);

    /**
     * 是否有权限使用该处理器功能
     *
     * @param app     钉钉H5微应用
     * @param payload 机器人消息回调请求体
     * @return 是否有权限
     */
    default boolean authorized(IDingMiniH5 app, PAYLOAD payload) {
        return true;
    }

    /**
     * 忽略该功能的App集合
     * 默认匹配就执行
     *
     * @return 忽略该功能的App集合
     */
    default Set<Class<? extends IDingMiniH5>> ignoredApps() {
        return Collections.emptySet();
    }

}
