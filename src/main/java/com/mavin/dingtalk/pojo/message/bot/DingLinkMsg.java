package com.mavin.dingtalk.pojo.message.bot;

import cn.hutool.core.text.CharSequenceUtil;
import com.google.common.base.Preconditions;
import lombok.Value;

/**
 * @author Mavin
 */
public interface DingLinkMsg extends IDingBotMsg {

    @Value
    class SampleLink implements DingLinkMsg {
        String messageUrl;
        String picUrl;
        String title;
        String text;

        public SampleLink(String messageUrl, String picUrl, String title, String text) {
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(messageUrl));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(title));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(text));
            this.messageUrl = messageUrl;
            this.picUrl = picUrl;
            this.title = title;
            this.text = text;
        }
    }
}
