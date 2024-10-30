package com.mavin.dingtalk.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Mavin
 */
@Data
@ConfigurationProperties(prefix = "dingtalk")
public class DingTalkProperties {

    private DingTalkMiniH5Properties miniH5;



}
