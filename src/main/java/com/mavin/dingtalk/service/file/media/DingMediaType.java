package com.mavin.dingtalk.service.file.media;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mavin
 */
@Getter
@AllArgsConstructor
public enum DingMediaType {

    /**
     * <a href="https://open.dingtalk.com/document/orgapp/upload-media-files">媒体文件类型</a>
     */
    IMAGE("image", "jpg,gif,png,bmp", 20 * 1024 * 1024 * 8),
    FILE("file", "doc,docx,xls,xlsx,ppt,pptx,zip,pdf,rar", 20 * 1024 * 1024 * 8),
    VOICE("voice", "amr,mp3,wav", 20 * 1024 * 1024 * 8),
    VIDEO("video", "mp4", 20 * 1024 * 1024 * 8),

    ;

    /**
     * 媒体类型
     */
    private final String code;
    /**
     * 支持的文件后缀
     */
    private final String suffix;
    /**
     * 文件最大bit
     */
    private final long maxSize;

}
