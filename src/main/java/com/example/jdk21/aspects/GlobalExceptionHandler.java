package com.example.jdk21.aspects;

import com.example.jdk21.enums.ResultCode;
import com.example.jdk21.exception.BizException;
import com.example.jdk21.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;
import java.util.Objects;

/**
 * @author admin
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e) {
        log.error("请求异常", e);
        if (e instanceof BizException exception) {
            return Result.failure(exception.getCode(), e.getMessage());
        }
        return Result.failure(ResultCode.SYSTEM_ERROR.getCode(), e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result exceptionHandler(MethodArgumentNotValidException e) {
        log.error("参数校验异常", e);
        return Result.failure(ResultCode.SYSTEM_ERROR.getCode(), Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SQLException.class)
    public Result exceptionHandler(SQLException e) {
        log.error("数据库异常", e);
        return Result.failure();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadSqlGrammarException.class)
    public Result exceptionHandler(BadSqlGrammarException e) {
        log.error("数据库异常", e);
        return Result.failure();
    }
}
