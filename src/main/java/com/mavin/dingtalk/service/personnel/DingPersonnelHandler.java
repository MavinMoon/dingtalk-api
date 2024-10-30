package com.mavin.dingtalk.service.personnel;

import cn.hutool.core.text.StrPool;
import com.dingtalk.api.request.OapiSmartworkHrmEmployeeQueryonjobRequest;
import com.dingtalk.api.response.OapiSmartworkHrmEmployeeQueryonjobResponse.PageResult;
import com.mavin.dingtalk.component.application.IDingMiniH5;
import com.mavin.dingtalk.service.AbstractDingApiHandler;
import com.mavin.dingtalk.service.IDingPersonnelHandler;
import com.mavin.dingtalk.constant.DingUrlConstant;
import lombok.NonNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mavin
 * @date 2024/6/28 17:40
 * @description 钉钉智能人事服务
 */
public class DingPersonnelHandler extends AbstractDingApiHandler implements IDingPersonnelHandler {

    public DingPersonnelHandler(@NonNull IDingMiniH5 app) {
        super(app);
    }

    @Override
    public PageResult getCurrentEmployeeList(@NonNull List<CurrentEmployeeStatus> statusList, @NonNull Long offset, @NonNull Long size) {
        String statusString = statusList.stream().map(CurrentEmployeeStatus::getValue)
                .collect(Collectors.joining(StrPool.COMMA));
        OapiSmartworkHrmEmployeeQueryonjobRequest request = new OapiSmartworkHrmEmployeeQueryonjobRequest();
        request.setStatusList(statusString);
        request.setOffset(offset);
        request.setSize(size);
        return execute(
                DingUrlConstant.Personnel.GET_CURRENT_EMPLOYEE_LIST,
                request,
                () -> "获取在职员工列表"
        ).getResult();
    }

}
