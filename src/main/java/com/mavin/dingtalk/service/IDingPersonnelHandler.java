package com.mavin.dingtalk.service;

import com.dingtalk.api.response.OapiSmartworkHrmEmployeeQueryonjobResponse.PageResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

/**
 * @author Mavin
 * @date 2024/6/28 17:34
 * @description 钉钉智能人事服务
 */
public interface IDingPersonnelHandler {

    /**
     * 2：试用期
     * <p>
     * 3：正式
     * <p>
     * 5：待离职
     * <p>
     * -1：无状态
     */
    @Getter
    @AllArgsConstructor
    enum CurrentEmployeeStatus {
        /***/
        PROBATION("2"),
        FORMAL("3"),
        RESIGNATION("5"),
        NO_STATUS("-1");

        private final String value;
    }

    /**
     * 获取在职员工列表
     *
     * @param statusList 在职员工状态列表
     * @param offset     分页游标，从0开始
     * @param size       分页大小，最大50
     * @return 在职员工列表
     */
    PageResult getCurrentEmployeeList(@NonNull List<CurrentEmployeeStatus> statusList, @NonNull Long offset, @NonNull Long size);


}
