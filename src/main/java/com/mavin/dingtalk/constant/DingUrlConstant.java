package com.mavin.dingtalk.constant;

import com.mavin.dingtalk.annotation.FieldDesc;
import lombok.experimental.UtilityClass;

/**
 * @author Mavin
 * @date 2024/6/24 15:57
 * @description 钉钉接口地址常量
 */
@UtilityClass
public class DingUrlConstant {

    @FieldDesc("钉钉互动卡片注册回调地址和路由键")
    public static final String REGISTER_CALLBACK = "https://oapi.dingtalk.com/topapi/im/chat/scencegroup/interactivecard/callback/register";

    @UtilityClass
    public static final class AccessToken {

        @FieldDesc("旧版SDK，获取企业内部应用的access_token")
        public static final String ACCESS_TOKEN_OLD = "https://oapi.dingtalk.com/gettoken";

    }

    @UtilityClass
    public static final class User {

        @FieldDesc("根据钉钉userId获取user")
        public static final String GET_USER_BY_USER_ID = "https://oapi.dingtalk.com/topapi/v2/user/get";

        @FieldDesc("旧版SDK，通过免登码获取用户信息")
        public static final String GET_USER_BY_LOGIN_AUTH_CODE = "https://oapi.dingtalk.com/topapi/v2/user/getuserinfo";

        @FieldDesc("旧版SDK，根据unionid获取用户userid")
        public static final String GET_USER_BY_LOGIN_AUTH_CODE_NEW = "https://oapi.dingtalk.com/topapi/user/getbyunionid";

    }

    @UtilityClass
    public static final class ImSceneGroup {

        @FieldDesc("通过OpenConversationId获取群信息")
        public static final String GET_GROUP_INFO_BY_OPEN_CONVERSATION_ID = "https://oapi.dingtalk.com/topapi/im/chat/scenegroup/get";

        @FieldDesc("通过ChatId获取群信息")
        public static final String GET_GROUP_INFO_BY_CHAT_ID = "https://oapi.dingtalk.com/chat/get";

    }

    @UtilityClass
    public static final class Personnel {

        @FieldDesc("获取在职员工列表")
        public static final String GET_CURRENT_EMPLOYEE_LIST = "https://oapi.dingtalk.com/topapi/smartwork/hrm/employee/queryonjob";

    }

    @UtilityClass
    public static final class Attendance {

        @FieldDesc("获取用户考勤数据")
        public static final String GET_USER_ATTENDANCE_DATA = "https://oapi.dingtalk.com/topapi/attendance/getupdatedata";

        @FieldDesc("获取考勤报表列定义")
        public static final String GET_ATTENDANCE_REPORT_COLUMN_DEFINITION = "https://oapi.dingtalk.com/topapi/attendance/getattcolumns";

        @FieldDesc("获取考勤报表列值")
        public static final String GET_ATTENDANCE_REPORT_COLUMN_VALUE = "https://oapi.dingtalk.com/topapi/attendance/getcolumnval";

    }

}
