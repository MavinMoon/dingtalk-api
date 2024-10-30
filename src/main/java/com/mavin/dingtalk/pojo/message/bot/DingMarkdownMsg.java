package com.mavin.dingtalk.pojo.message.bot;

import cn.hutool.core.text.CharSequenceUtil;
import com.mavin.dingtalk.utils.DingMarkdown;
import com.google.common.base.Preconditions;
import lombok.Value;

import java.util.Objects;


/**
 * @author Mavin
 */
public interface DingMarkdownMsg extends IDingBotMsg{

    @Value
    class SampleMarkdown implements DingMarkdownMsg {
        String title;
        String text;

        public SampleMarkdown(String title, DingMarkdown.Builder markdownBuilder) {
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(title));
            Preconditions.checkArgument(Objects.nonNull(markdownBuilder));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(markdownBuilder.build()));
            this.title = title;
            this.text = markdownBuilder.build();
        }

        public SampleMarkdown(String title, String markdown) {
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(title));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(markdown));
            this.title = title;
            this.text = markdown;
        }
    }
}
