package com.example.jdk21.pojo;

import com.alibaba.fastjson2.annotation.JSONField;
import com.example.jdk21.enums.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author admin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result implements Serializable {

    @JSONField(ordinal = 0)
    private Integer code;

    @JSONField(ordinal = 1)
    private String message;

    @JSONField(ordinal = 2)
    private Object data;

    public Result(Integer code, Object data) {
        this.code = code;
        this.data = data;
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Result success() {
        return new Result(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }

    public static Result success(String message) {
        return new Result(ResultCode.SUCCESS.getCode(), message);
    }

    public static <T> Result success(T data) {
        return new Result(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    public static Result failure() {
        return failure(ResultCode.SYSTEM_ERROR.getCode(), ResultCode.SYSTEM_ERROR.getMessage());
    }

    public static Result failure(String message) {
        return failure(ResultCode.SYSTEM_ERROR.getCode(), message);
    }

    public static Result failure(Integer code, String message) {
        return new Result(code, message);
    }
}
