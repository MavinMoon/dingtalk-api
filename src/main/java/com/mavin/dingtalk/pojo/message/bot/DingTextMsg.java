
package com.mavin.dingtalk.pojo.message.bot;


import cn.hutool.core.text.CharSequenceUtil;
import com.google.common.base.Preconditions;
import lombok.Value;


/**
 * @author Mavin
 */
public interface DingTextMsg extends IDingBotMsg {

    @Value
    class SampleText implements DingTextMsg {
        String content;

        public SampleText(String content) {
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(content));
            this.content = content;
        }
    }
}
