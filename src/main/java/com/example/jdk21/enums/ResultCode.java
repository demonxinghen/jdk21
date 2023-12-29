package com.example.jdk21.enums;

/**
 * @author admin
 */
public enum ResultCode {
    SUCCESS(0, "操作成功"),
    ERROR(300,"操作失败"),
    SYSTEM_ERROR(3000, "系统错误"),
    TOKEN_IS_EXPIRED_ERROR(6003, "token过期，请重新登录。"),
    TOKEN_IS_INVALID_ERROR(6004, "token无效，请重新登录。"),
    TOKEN_IS_ERROR(6005, "非法的token。"),
    TOKEN_IS_NULL(6006, "token不能为空"),
    SIGN_IS_ERROR(6007, "验签失败。"),
    ACCOUNT_STATE_ERROR(6008, "非法的用户状态"),
    MYSQL_EXECUTE_ERROR(6009, "MYSQL 执行错误"),
    USER_NOT_EXIST_ERROR(1001, "用户不存在"),
    USERNAME_IS_EXIST_ERROR(1002, "登录名已存在"),
    USERNAME_OR_PWD_ERROR(1003, "用户名或者密码错误"),
    ACCOUNT_NAME_IS_REGISTER(1004, "该账号已被注册，请重新修改登录名"),
    ACCOUNT_IS_FORBIDDEN_ERROR(1005, "登录失败【账号已禁用,请联系系统管理员】"),
    ACCOUNT_IS_LOCKED_ERROR(1006, "登录失败【账号已锁定，请联系系统管理员】"),
    PHONE_IS_BINDING(1007, "手机号码已被其它账号绑定"),
    SEND_CODE_FAILED(1008, "发送验证码失败"),
    SMS_CODE_INVALID(1009, "短信验证码无效"),
    SMS_CODE_IS_SEND_TOO_MUCH(1010, "短信验证码获取次数已达到当日最大上限"),
    UNAUTHORIZED(1011, "无权操作"),
    CUSTOMER_DISABLED(1012, "所在客户已被禁用"),
    CUSTOMER_IS_NOT_EXISTS(1013, "关联大客户不存在"),
    DATA_IS_EXISTS(1014, "采购价信息已存在"),
    DATA_NOT_CHANGE(1015,"信息未更改"),

    PARAM_IS_NULL_ERROR(2001, "参数不能为空"),
    PARAM_VALUE_IS_ERROR(2002, "参数值有误");


    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
