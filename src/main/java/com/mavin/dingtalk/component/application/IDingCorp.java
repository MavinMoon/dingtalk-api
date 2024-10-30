package com.mavin.dingtalk.component.application;

import com.mavin.dingtalk.annotation.MethodDesc;

/**
 * @author Mavin
 * @date 2024/6/24 14:33
 * @description 钉钉组织
 */
public interface IDingCorp {

    /**
     * 获取企业id
     *
     * @return 企业id
     */
    @MethodDesc(value = "获取企业id")
    String getCorpId();

}
