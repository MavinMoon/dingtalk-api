package com.mavin.dingtalk.pojo.message.bot;

import cn.hutool.core.text.CharSequenceUtil;
import com.google.common.base.Preconditions;
import com.google.gson.annotations.SerializedName;
import lombok.Value;

/**
 * @author Mavin
 */
public interface DingActionCardMsg extends IDingBotMsg {

    /**
     * 卡片消息：一个按钮
     */
    @Value
    class SampleActionCard implements DingActionCardMsg {
        String title;
        String text;
        String singleTitle;
        @SerializedName("singleURL")
        String singleUrl;

        public SampleActionCard(String title, String text, String singleTitle, String singleUrl) {
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(title));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(text));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(singleTitle));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(singleUrl));
            this.title = title;
            this.text = text;
            this.singleTitle = singleTitle;
            this.singleUrl = singleUrl;
        }
    }

    /**
     * 卡片消息：竖向两个按钮
     */
    @Value
    class SampleActionCard2 implements DingActionCardMsg {
        String title;
        String text;
        String actionTitle1;
        @SerializedName("actionURL1")
        String actionUrl1;
        String actionTitle2;
        @SerializedName("actionURL2")
        String actionUrl2;

        public SampleActionCard2(String title, String text, String actionTitle1, String actionUrl1, String actionTitle2, String actionUrl2) {
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(title));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(text));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(actionTitle1));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(actionUrl1));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(actionTitle2));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(actionUrl2));
            this.title = title;
            this.text = text;
            this.actionTitle1 = actionTitle1;
            this.actionUrl1 = actionUrl1;
            this.actionTitle2 = actionTitle2;
            this.actionUrl2 = actionUrl2;
        }
    }

    /**
     * 卡片消息：竖向三个按钮
     */
    @Value
    class SampleActionCard3 implements DingActionCardMsg {
        String title;
        String text;
        String actionTitle1;
        @SerializedName("actionURL1")
        String actionUrl1;
        String actionTitle2;
        @SerializedName("actionURL2")
        String actionUrl2;
        String actionTitle3;
        @SerializedName("actionURL3")
        String actionUrl3;

        public SampleActionCard3(String title, String text, String actionTitle1, String actionUrl1, String actionTitle2, String actionUrl2, String actionTitle3, String actionUrl3) {
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(title));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(text));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(actionTitle1));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(actionUrl1));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(actionTitle2));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(actionUrl2));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(actionTitle3));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(actionUrl3));
            this.title = title;
            this.text = text;
            this.actionTitle1 = actionTitle1;
            this.actionUrl1 = actionUrl1;
            this.actionTitle2 = actionTitle2;
            this.actionUrl2 = actionUrl2;
            this.actionTitle3 = actionTitle3;
            this.actionUrl3 = actionUrl3;
        }
    }

    /**
     * 卡片消息：竖向四个按钮
     */
    @Value
    class SampleActionCard4 implements DingActionCardMsg {
        String title;
        String text;
        String actionTitle1;
        @SerializedName("actionURL1")
        String actionUrl1;
        String actionTitle2;
        @SerializedName("actionURL2")
        String actionUrl2;
        String actionTitle3;
        @SerializedName("actionURL3")
        String actionUrl3;
        String actionTitle4;
        @SerializedName("actionURL4")
        String actionUrl4;

        public SampleActionCard4(String title, String text, String actionTitle1, String actionUrl1, String actionTitle2, String actionUrl2, String actionTitle3, String actionUrl3, String actionTitle4, String actionUrl4) {
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(title));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(text));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(actionTitle1));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(actionUrl1));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(actionTitle2));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(actionUrl2));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(actionTitle3));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(actionUrl3));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(actionTitle4));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(actionUrl4));
            this.title = title;
            this.text = text;
            this.actionTitle1 = actionTitle1;
            this.actionUrl1 = actionUrl1;
            this.actionTitle2 = actionTitle2;
            this.actionUrl2 = actionUrl2;
            this.actionTitle3 = actionTitle3;
            this.actionUrl3 = actionUrl3;
            this.actionTitle4 = actionTitle4;
            this.actionUrl4 = actionUrl4;
        }
    }

    /**
     * 卡片消息：竖向五个按钮
     */
    @Value
    class SampleActionCard5 implements DingActionCardMsg {
        String title;
        String text;
        String actionTitle1;
        @SerializedName("actionURL1")
        String actionUrl1;
        String actionTitle2;
        @SerializedName("actionURL2")
        String actionUrl2;
        String actionTitle3;
        @SerializedName("actionURL3")
        String actionUrl3;
        String actionTitle4;
        @SerializedName("actionURL4")
        String actionUrl4;
        String actionTitle5;
        @SerializedName("actionURL5")
        String actionUrl5;

        public SampleActionCard5(String title, String text, String actionTitle1, String actionUrl1, String actionTitle2, String actionUrl2, String actionTitle3, String actionUrl3, String actionTitle4, String actionUrl4, String actionTitle5, String actionUrl5) {
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(title));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(text));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(actionTitle1));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(actionUrl1));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(actionTitle2));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(actionUrl2));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(actionTitle3));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(actionUrl3));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(actionTitle4));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(actionUrl4));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(actionTitle5));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(actionUrl5));
            this.title = title;
            this.text = text;
            this.actionTitle1 = actionTitle1;
            this.actionUrl1 = actionUrl1;
            this.actionTitle2 = actionTitle2;
            this.actionUrl2 = actionUrl2;
            this.actionTitle3 = actionTitle3;
            this.actionUrl3 = actionUrl3;
            this.actionTitle4 = actionTitle4;
            this.actionUrl4 = actionUrl4;
            this.actionTitle5 = actionTitle5;
            this.actionUrl5 = actionUrl5;
        }
    }

    /**
     * 卡片消息：横向二个按钮。
     */
    @Value
    class SampleActionCard6 implements DingActionCardMsg {
        String title;
        String text;
        String buttonTitle1;
        String buttonUrl1;
        String buttonTitle2;
        String buttonUrl2;

        public SampleActionCard6(String title, String text, String buttonTitle1, String buttonUrl1, String buttonTitle2, String buttonUrl2) {
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(title));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(text));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(buttonTitle1));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(buttonUrl1));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(buttonTitle2));
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(buttonUrl2));
            this.title = title;
            this.text = text;
            this.buttonTitle1 = buttonTitle1;
            this.buttonUrl1 = buttonUrl1;
            this.buttonTitle2 = buttonTitle2;
            this.buttonUrl2 = buttonUrl2;
        }
    }
}
