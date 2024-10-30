package com.mavin.dingtalk.utils;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import com.mavin.dingtalk.annotation.MethodDesc;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mavin
 * @date 2024/6/21 17:33
 * @description 钉钉Markdown工具类，用于生成钉钉Markdown格式的文本，建造者模式
 */
public class DingMarkdown {

    /* 钉钉支持的Markdown语法 */
    /*  # 一级标题
        ## 二级标题
        ### 三级标题
        #### 四级标题
        ##### 五级标题

        正文 **加粗文本** *斜体文本* [钉钉官网链接](https://www.dingtalk.com/) [元气满满]


        第一段第一行<br>第一段第二行

        第二段第一行<br>第二段第二行

        ***

        > 引用第一段
        >
        >> 嵌套引用第二段
        >
        > 引用第三段

        1. 第一个有序列表项
            * 第一个无序列表项
                * 第一个嵌套无序列表项
                * 第二个嵌套无序列表项
            * 第二个无序列表项
        2. 第二个有序列表项
        3. 第三个有序列表项

        | 表格标题1 | 表格标题2 | 表格标题3 |
        | :- | :-: | -: |
        | 左对齐内容1 | 剧中内容1 | 右对齐内容1 |
        | 左对齐内容2 | 剧中内容2 | 右对齐内容2 |

        ```json
        {
          "firstName": "John",
          "lastName": "Smith",
          "age": 25
        }
        ```
     */

    private final List<String> fullContent;

    public DingMarkdown(Builder builder) {
        fullContent = builder.fullContent;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        List<String> fullContent = new ArrayList<>();


        @MethodDesc(value = "一级标题")
        public Builder level1Title(String level1Title) {
            fullContent.add("# " + level1Title);
            return this;
        }

        @MethodDesc(value = "二级标题")
        public Builder level2Title(String level2Title) {
            fullContent.add("## " + level2Title);
            return this;
        }

        @MethodDesc(value = "三级标题")
        public Builder level3Title(String level3Title) {
            fullContent.add("### " + level3Title);
            return this;
        }

        @MethodDesc(value = "四级标题")
        public Builder level4Title(String level4Title) {
            fullContent.add("#### " + level4Title);
            return this;
        }

        @MethodDesc(value = "五级标题")
        public Builder level5Title(String level5Title) {
            fullContent.add("##### " + level5Title);
            return this;
        }

        @MethodDesc(value = "六级标题")
        public Builder level6Title(String level6Title) {
            fullContent.add("###### " + level6Title);
            return this;
        }

        @MethodDesc(value = "正文")
        public Builder text(String text) {
            fullContent.add(text);
            return this;
        }

        @MethodDesc(value = "帶字体样式的正文")
        public Font textWithFont(String text) {
            return new Font(this, text);
        }

        @MethodDesc(value = "加粗")
        public Builder boldText(String boldText) {
            fullContent.add("**" + boldText + "**");
            return this;
        }

        @MethodDesc(value = "斜体")
        public Builder italicText(String italicText) {
            fullContent.add("*" + italicText + "*");
            return this;
        }

        @MethodDesc(value = "换行")
        public Builder lineBreak() {
            fullContent.add("</br>");
            return this;
        }

        @MethodDesc(value = "段落")
        public Builder paragraph(String... paragraph) {
            if (paragraph.length > 0) {
                final String elements = Arrays.stream(paragraph).map(i -> i + "</br>")
                        .collect(Collectors.joining("</br>"));
                fullContent.add(elements);
            }
            return this;
        }

        @MethodDesc(value = "分割线")
        public Builder splitLine() {
            fullContent.add("***");
            return this;
        }

        @MethodDesc(value = "链接")
        public Builder link(String name, String url) {
            fullContent.add("[" + name + "](" + url + ")");
            return this;
        }

        @MethodDesc(value = "图片")
        public Builder image(String url) {
            fullContent.add("![](" + url + ")");
            return this;
        }

        @MethodDesc(value = "单层引用段落")
        public Builder monolayerQuote(String... element) {
            if (element.length > 0) {
                final String elements = Arrays.stream(element).map(i -> "> " + i + "</br>")
                        .collect(Collectors.joining("> "));
                fullContent.add(elements);
            }
            return this;
        }

        @MethodDesc(value = "单层无序列表")
        public Builder monolayerDisorderedList(String... element) {
            if (element.length > 0) {
                final String elements = Arrays.stream(element).map(i -> "* " + i)
                        .collect(Collectors.joining("</br>"));
                fullContent.add(elements);
            }
            return this;
        }

        @MethodDesc(value = "单层有序列表")
        public Builder monolayerOrderedList(String... element) {
            if (element.length > 0) {
                StringBuilder strBuilder = new StringBuilder();
                for (int i = 0; i < element.length; i++) {
                    strBuilder.append(i).append(". ").append(element[i]).append("</br>");
                }
                fullContent.add(strBuilder.toString());
            }
            return this;
        }

        @MethodDesc(value = "围栏代码块")
        public Builder codeBlock(String language, String code) {
            if (CharSequenceUtil.isNullOrUndefined(language)) {
                language = "";
            }
            if (CharSequenceUtil.isNullOrUndefined(code)) {
                code = "";
            }
            fullContent.add("```" + language + "</br>" + code + "</br>```");
            return this;
        }

        public String build() {
            final DingMarkdown dingMarkdown = new DingMarkdown(this);
            return String.join("</br>", dingMarkdown.fullContent);
        }
    }

    public static class Font {

        private String temp = "<font face=\"{face}\" color=\"{color}\">{text}</font>";
        private final Builder builder;


        public Font(Builder builder, String text) {
            this.builder = builder;
            format("text", text);
        }

        public Font color(String hexColor) {
            format("color", !StringUtils.hasText(hexColor) ? "#FFFFFF" : hexColor);
            return this;
        }

        public Font face(String fontFace) {
            format("face", !StringUtils.hasText(fontFace) ? fontFace : "monospace");
            return this;
        }

        public Builder builder() {
            correctTemp();
            builder.fullContent.add(temp);
            return builder;
        }

        private void correctTemp() {
            temp = temp.replace("{face}", "monospace").replace("{color}", "#FFFFFF");
        }

        private void format(String mark, String replacement) {
            temp = temp.replace("{" + mark + "}", replacement);
        }
    }


    public static class FontWithCard {

        private String temp = "<font sizeToken=\"{sizeKey}\" colorTokenV2=\"{colorKey}\">{text}</font>";
        private final Builder builder;


        public FontWithCard(Builder builder, String text) {
            this.builder = builder;
            format("text", text);
        }

        /**
         * @param size 字体大小，默认common_body_text_style__font_size
         * @return this
         */
        @MethodDesc(value = "字体大小")
        public FontWithCard sizeToken(FontStyleSizeEnum size) {
            format("sizeToken", ObjectUtil.isNotNull(size) ? size.getKey()
                    : FontStyleSizeEnum.COMMON_BODY_FONT_SIZE.getKey());
            return this;
        }

        /**
         * @param color 颜色，默认common_level1_base_color
         * @return this
         */
        @MethodDesc(value = "字体颜色")
        public FontWithCard colorTokenV2(FontStyleColorEnum color) {
            format("colorKey", ObjectUtil.isNotNull(color) ? color.getKey()
                    : FontStyleColorEnum.COMMON_LEVEL1_BASE.getKey());
            return this;
        }

        public Builder builder() {
            correctTemp();
            builder.fullContent.add(temp);
            return builder;
        }

        /**
         * 替换默认值
         */
        @MethodDesc(value = "替换默认值")
        private void correctTemp() {
            temp = temp.replace("{colorKey}", FontStyleColorEnum.COMMON_LEVEL1_BASE.getKey())
                    .replace("{sizeKey}", FontStyleSizeEnum.COMMON_BODY_FONT_SIZE.getKey());
        }

        @MethodDesc(value = "格式化")
        private void format(String mark, String replacement) {
            temp = temp.replace("{" + mark + "}", replacement);
        }

        @Getter
        @AllArgsConstructor
        public enum FontStyleSizeEnum {

            COMMON_HYPERTITLE_FONT_SIZE("common_hypertitle_text_style__font_size"),
            COMMON_LARGETITLE_FONT_SIZE("common_largetitle_text_style__font_size"),
            COMMON_H1_FONT_SIZE("common_h1_text_style__font_size"),
            COMMON_H2_FONT_SIZE("common_h2_text_style__font_size"),
            COMMON_H3_FONT_SIZE("common_h3_text_style__font_size"),
            COMMON_H4_FONT_SIZE("common_h4_text_style__font_size"),
            COMMON_H5_FONT_SIZE("common_h5_text_style__font_size"),
            COMMON_BODY_FONT_SIZE("common_body_text_style__font_size"),
            COMMON_FOOTNOTE_FONT_SIZE("common_footnote_text_style__font_size"),

            ;

            private final String key;

            public static FontStyleSizeEnum getEnumByKey(String key) {
                return Arrays.stream(FontStyleSizeEnum.values())
                        .filter(i -> i.getKey().equals(key))
                        .findFirst()
                        .orElse(null);
            }
        }

        @Getter
        @AllArgsConstructor
        public enum FontStyleColorEnum {

            COMMON_YELLOW1("common_yellow1_color"),
            COMMON_ORANGE1("common_orange1_color"),
            COMMON_RED1("common_red1_color"),
            COMMON_PINK1("common_pink1_color"),
            COMMON_PURPLE1("common_purple1_color"),
            COMMON_BLUE1("common_blue1_color"),
            COMMON_WATER1("common_water1_color"),
            COMMON_OLIVE1("common_olive1_color"),
            COMMON_GREEN1("common_green1_color"),
            COMMON_LEVEL1_BASE("common_level1_base_color"),
            COMMON_LEVEL2_BASE("common_level2_base_color"),
            COMMON_LEVEL3_BASE("common_level3_base_color"),
            COMMON_LEVEL4_BASE("common_level4_base_color"),
            COMMON_GRAY1("common_gray1_color"),
            COMMON_GRAY2("common_gray2_color"),
            COMMON_GRAY3("common_gray3_color"),
            COMMON_GRAY4("common_gray4_color"),
            COMMON_GRAY5("common_gray5_color"),
            COMMON_GRAY6("common_gray6_color");

            private final String key;

            public static FontStyleColorEnum getEnumByKey(String key) {
                return Arrays.stream(FontStyleColorEnum.values())
                        .filter(i -> i.getKey().equals(key))
                        .findFirst()
                        .orElse(null);
            }

        }

    }


}
