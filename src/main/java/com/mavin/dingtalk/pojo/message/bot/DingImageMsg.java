package com.mavin.dingtalk.pojo.message.bot;

import cn.hutool.core.text.CharSequenceUtil;
import com.google.common.base.Preconditions;
import com.google.gson.annotations.SerializedName;
import lombok.Value;

/**
 * @author Mavin
 */
public interface DingImageMsg extends IDingBotMsg {

    @Value
    class SampleImageMsg implements DingImageMsg {
        @SerializedName("photoURL")
        String photoUrl;

        public SampleImageMsg(String photoUrl) {
            Preconditions.checkArgument(CharSequenceUtil.isNotEmpty(photoUrl));
            this.photoUrl = photoUrl;
        }
    }
}
