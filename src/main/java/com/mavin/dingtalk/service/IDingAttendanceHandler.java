package com.mavin.dingtalk.service;

import com.dingtalk.api.response.OapiAttendanceGetattcolumnsResponse;
import com.dingtalk.api.response.OapiAttendanceGetcolumnvalResponse;
import com.dingtalk.api.response.OapiAttendanceGetupdatedataResponse;
import lombok.NonNull;

import java.util.Date;
import java.util.List;

/**
 * @author Mavin
 * @date 2024/7/5 17:08
 * @description 钉钉考勤API
 */
public interface IDingAttendanceHandler {


    /**
     * 获取用户考勤数据
     *
     * @param userId   钉钉用户ID
     * @param workDate 考勤日期
     * @return 考勤数据
     */
    OapiAttendanceGetupdatedataResponse.AtCheckInfoForOpenVo getUserAttendanceData(
            @NonNull String userId, @NonNull Date workDate
    );

    /**
     * 获取考勤报表列定义
     *
     * @return 考勤报表列定义
     */
    OapiAttendanceGetattcolumnsResponse.AttColumnsForTopVo getAttendanceReportColumnDefinition();

    /**
     * 获取考勤报表列值
     *
     * @param userId       钉钉用户ID
     * @param columnIdList 报表列ID
     * @param fromDate     开始时间
     * @param toDate       结束时间
     * @return 考勤报表列值
     */
    OapiAttendanceGetcolumnvalResponse.ColumnValListForTopVo getAttendanceReportColumnValue(
            @NonNull String userId, @NonNull List<String> columnIdList, @NonNull Date fromDate, @NonNull Date toDate
    );

}
