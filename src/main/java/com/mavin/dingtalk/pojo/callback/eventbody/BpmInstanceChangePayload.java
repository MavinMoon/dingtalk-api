package com.mavin.dingtalk.pojo.callback.eventbody;

import lombok.Data;

/**
 * @author Mavin
 * @date 2024/11/26 12:12
 * @description 审批实例开始、结束、终止、删除事件推送参数
 */
@Data
public class BpmInstanceChangePayload implements IDingMiniH5EventBody {

    /**
     * 审批实例id
     */
    private String processInstanceId;

    /**
     * 结束审批实例时间。时间戳，单位毫秒。
     */
    private Long finishTime;

    /**
     * 创建审批实例时间。时间戳，单位毫秒。
     */
    private Long createTime;

    /**
     * 审批模板的唯一码。
     */
    private String processCode;

    /**
     * 业务分类标识。
     */
    private String bizCategoryId;

    /**
     * 流程实例业务标识。
     */
    private String businessId;

    /**
     * 发起审批实例的员工userId。
     */
    private String staffId;

    /**
     * 审批实例名称
     */
    private String title;

    /**
     * 实例状态变更类型：
     * start：审批实例开始
     * finish：审批正常结束（同意或拒绝）
     * terminate：审批终止（发起人撤销审批单）
     * delete：审批实例删除
     */
    private String type;

    /**
     * 业务身份。
     */
    private String businessType;

    /**
     * 审批结果(审批终止时无此参数)：
     * agree： 同意
     * refuse：拒绝
     */
    private String result;

    /**
     * 审批实例url，可在钉钉内跳转到审批页面。
     */
    private String url;

}
