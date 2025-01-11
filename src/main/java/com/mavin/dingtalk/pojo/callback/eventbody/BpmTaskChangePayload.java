package com.mavin.dingtalk.pojo.callback.eventbody;

import lombok.Data;

/**
 * @author Mavin
 * @date 2025/1/8 12:26
 * @description 审批任务开始、结束、取消事件推送参数
 */
@Data
public class BpmTaskChangePayload implements IDingMiniH5EventBody {

    /**
     * 审批结果：
     * agree：同意
     * refuse：拒绝
     * redirect：表示审批任务转交
     * audit：表示当前节点为办理人节点，audit为办理结果
     */
    private String result;

    /**
     * 审批实例id。
     */
    private String processInstanceId;

    /**
     * 结束任务的时间。时间戳，单位毫秒。
     * 审批开始无该数据。
     */
    private Long finishTime;

    /**
     * 创建任务的时间。时间戳，单位毫秒。
     */
    private Long createTime;

    /**
     * 审批模板的唯一码。
     */
    private String processCode;

    /**
     * 业务类目。
     */
    private String bizCategoryId;

    /**
     * 流程实例业务标识
     */
    private String businessId;

    /**
     * 任务状态变更类型：
     * start：审批任务开始
     * finish：审批任务正常结束（完成或转交）
     * cancel：说明当前节点有多个审批人并且是或签，其中一个人执行了审批，其他审批人会推送cancel类型事件
     * comment：审批任务评论。
     */
    private String type;

    /**
     * 实例标题。
     */
    private String title;

    /**
     * 任务id。
     */
    private Long taskId;

    /**
     * 用户userId：
     * 当前任务的审批人userId
     * 操作转交动作的用户userId
     */
    private String staffId;

}
