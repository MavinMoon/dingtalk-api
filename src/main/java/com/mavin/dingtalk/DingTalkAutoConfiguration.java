package com.mavin.dingtalk;

import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.mavin.dingtalk.component.DingBooter;
import com.mavin.dingtalk.component.application.IDingMiniH5;
import com.mavin.dingtalk.component.configuration.IDingBotMsgCallbackConfig;
import com.mavin.dingtalk.component.configuration.IDingMiniH5EventCallbackConfig;
import com.mavin.dingtalk.component.factory.app.DingAppFactory;
import com.mavin.dingtalk.config.DingTalkProperties;
import com.mavin.dingtalk.listener.DefaultDingMiniH5EventListener;
import com.mavin.dingtalk.pojo.message.interactive.callback.IDingInteractiveCardCallBack;
import com.mavin.dingtalk.service.callback.minih5.IDingMiniH5EventCallbackHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.*;

/**
 * @author Mavin
 * @date 2024/06/21
 * @description 自动配置类
 */
@Configuration
@EnableConfigurationProperties(DingTalkProperties.class)
public class DingTalkAutoConfiguration {

    @Bean
    public DingBooter dingBooter(List<IDingInteractiveCardCallBack> callBacks) {
        return new DingBooter(callBacks);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "dingtalk.miniH5.bot", name = "streamEnabled", havingValue = "true")
    public IDingBotMsgCallbackConfig dingBotMsgCallbackStreamConfig() {
        return () -> DingAppFactory.getAppByClazz(IDingMiniH5.class);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "dingtalk.miniH5.bot", name = "httpEnabled", havingValue = "true")
    public IDingBotMsgCallbackConfig dingBotMsgCallbackHttpConfig() {
        return () -> DingAppFactory.getAppByClazz(IDingMiniH5.class);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "dingtalk.miniH5.event", name = "streamEnabled", havingValue = "true")
    public IDingMiniH5EventCallbackConfig dingMiniH5EventCallbackStreamConfig() {
        return () -> DingAppFactory.getAppByClazz(IDingMiniH5.class);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "dingtalk.miniH5.event", name = "httpEnabled", havingValue = "true")
    public IDingMiniH5EventCallbackConfig dingMiniH5EventCallbackHttpConfig() {
        return () -> DingAppFactory.getAppByClazz(IDingMiniH5.class);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "dingtalk.miniH5.event.listener", name = "defaultEnabled", havingValue = "true")
    public DefaultDingMiniH5EventListener defaultDingMiniH5EventListener(
            List<IDingMiniH5EventCallbackHandler> miniH5EventCallbackHandlers,
            @Qualifier("defaultDingTalkExecutor") ExecutorService defaultDingTalkExecutor
    ) {
        return new DefaultDingMiniH5EventListener(miniH5EventCallbackHandlers, defaultDingTalkExecutor);
    }

    @Bean(name = "defaultDingTalkExecutor")
    @ConditionalOnMissingBean(name = {"defaultDingTalkExecutor"})
    public ExecutorService defaultDingTalkExecutor() {
        // 创建自定义线程工厂
        ThreadFactoryBuilder threadFactoryBuilder = new ThreadFactoryBuilder();
        threadFactoryBuilder.setNamePrefix("DingTalk-Thread-");
        threadFactoryBuilder.setDaemon(true);
        ThreadFactory basicThreadFactory = threadFactoryBuilder.build();
        // 使用ExecutorBuilder构建线程池
        return ExecutorBuilder.create()
                .setCorePoolSize(0)
                .setMaxPoolSize(Runtime.getRuntime().availableProcessors())
                .setKeepAliveTime(60, TimeUnit.SECONDS)
                .setWorkQueue(new LinkedBlockingQueue<>(1024))
                .setThreadFactory(basicThreadFactory)
                .setHandler(new ThreadPoolExecutor.CallerRunsPolicy())
                .build();
    }

}
