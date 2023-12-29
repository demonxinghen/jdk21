package com.example.jdk21.exception;

import com.example.jdk21.enums.ResultCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author admin
 * @date 2023/12/27 16:03
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BizException extends RuntimeException {

    private Integer code;

    private String message;

    public BizException(String message) {
        super();
        this.message = message;
        this.code = ResultCode.SYSTEM_ERROR.getCode();
    }

    public BizException(Integer code, String message) {
        super();
        this.message = message;
        this.code = code;
    }
}
