package com.jy.rbac.handler;

import cn.dev33.satoken.exception.NotLoginException;
import com.jy.rbac.constant.MessageConstant;
import com.jy.rbac.exception.BaseException;
import com.jy.rbac.pojo.common.Result;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.BindException;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 捕获业务异常
     */
    @ExceptionHandler(BaseException.class)
    public Result<Object> exceptionHandler(BaseException ex){
        log.error("异常信息：{}",ex.getMessage());
        return Result.error(ex.getMessage());
    }
    /**
     * 处理 @RequestBody 参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Object> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex){
        log.error("异常信息：{}",ex.getMessage());
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": "+fieldError.getDefaultMessage())
                .collect(Collectors.joining(";"));
        return Result.error(message);
    }
    /**
     * 处理 @ModelAttribute / @RequestParam 参数校验异常
     */
    @ExceptionHandler(BindException.class)
    public Result handleBindException(BindException e){
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": "+fieldError.getDefaultMessage())
                .collect(Collectors.joining(";"));
        return Result.error(message);
    }
    /**
     * 处理单个参数校验异常（@Validated 用在类上时）
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result handleConstraintViolationException(ConstraintViolationException e){
        String message = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("; "));
        return Result.error(message);
    }
    /**
     * token失效异常，返回401，前端据此跳转登录页
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotLoginException.class)
    public Result handleNotLoginException(NotLoginException e){
        return Result.error(MessageConstant.ACCOUNT_LOGIN_STATUS_FAILED);
    }
    /**
     * 数据库唯一索引冲突（并发插入或逻辑删除占名等场景），返回友好提示
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public Result handleDuplicateKeyException(DuplicateKeyException e){
        log.error("唯一键冲突：{}", e.getMessage());
        return Result.error(MessageConstant.DATA_DUPLICATE);
    }
    /**
     * 兜底
     *
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        log.error("系统异常", e);
        return Result.error(MessageConstant.SERVER_ERROR);
    }
}
