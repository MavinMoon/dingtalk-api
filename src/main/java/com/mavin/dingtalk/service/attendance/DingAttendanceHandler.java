package com.mavin.dingtalk.service.attendance;

import cn.hutool.core.text.StrPool;
import com.dingtalk.api.request.OapiAttendanceGetattcolumnsRequest;
import com.dingtalk.api.request.OapiAttendanceGetcolumnvalRequest;
import com.dingtalk.api.request.OapiAttendanceGetupdatedataRequest;
import com.dingtalk.api.response.OapiAttendanceGetattcolumnsResponse;
import com.dingtalk.api.response.OapiAttendanceGetcolumnvalResponse;
import com.dingtalk.api.response.OapiAttendanceGetupdatedataResponse;
import com.mavin.dingtalk.component.application.IDingApp;
import com.mavin.dingtalk.service.AbstractDingApiHandler;
import com.mavin.dingtalk.service.IDingAttendanceHandler;
import com.mavin.dingtalk.constant.DingUrlConstant;
import lombok.NonNull;

import java.util.Date;
import java.util.List;

/**
 * @author Mavin
 * @date 2024/7/5 17:13
 * @description 钉钉考勤服务
 */
public class DingAttendanceHandler extends AbstractDingApiHandler implements IDingAttendanceHandler {

    public DingAttendanceHandler(@NonNull IDingApp app) {
        super(app);
    }

    @Override
    public OapiAttendanceGetupdatedataResponse.AtCheckInfoForOpenVo getUserAttendanceData(@NonNull String userId, @NonNull Date workDate) {
        OapiAttendanceGetupdatedataRequest request = new OapiAttendanceGetupdatedataRequest();
        request.setUserid(userId);
        request.setWorkDate(workDate);
        return execute(DingUrlConstant.Attendance.GET_USER_ATTENDANCE_DATA, request, () -> "获取用户考勤数据").getResult();
    }

    @Override
    public OapiAttendanceGetattcolumnsResponse.AttColumnsForTopVo getAttendanceReportColumnDefinition() {
        OapiAttendanceGetattcolumnsRequest request = new OapiAttendanceGetattcolumnsRequest();
        return execute(DingUrlConstant.Attendance.GET_ATTENDANCE_REPORT_COLUMN_DEFINITION, request, () -> "获取考勤报表列定义").getResult();
    }

    @Override
    public OapiAttendanceGetcolumnvalResponse.ColumnValListForTopVo getAttendanceReportColumnValue(
            @NonNull String userId, @NonNull List<String> columnIdList, @NonNull Date fromDate, @NonNull Date toDate
    ) {
        OapiAttendanceGetcolumnvalRequest request = new OapiAttendanceGetcolumnvalRequest();
        request.setUserid(userId);
        request.setColumnIdList(String.join(StrPool.COMMA, columnIdList));
        request.setFromDate(fromDate);
        request.setToDate(toDate);
        return execute(DingUrlConstant.Attendance.GET_ATTENDANCE_REPORT_COLUMN_VALUE, request, () -> "获取考勤报表列值").getResult();
    }


}
