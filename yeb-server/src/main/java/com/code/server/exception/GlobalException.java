package com.code.server.exception;

import com.code.server.pojo.RespBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 * SpringBoot全局异常处理方式主要两种：
 * 1、使用 @ControllerAdvice 和 @ExceptionHandler 注解。
 * 2、使用 ErrorController类 来实现
 * 区别：
 * 1. @ControllerAdvice 方式只能处理控制器抛出的异常。此时请求已经进入控制器中。
 * 2. ErrorController类 方式可以处理所有的异常，包括未进入控制器的错误，比如404,401等错误
 * 3. 如果应用中两者共同存在，则 @ControllerAdvice 方式处理控制器抛出的异常，ErrorController类 方式处理未进入控制器的异常。
 * 4. @ControllerAdvice 方式可以定义多个拦截方法，拦截不同的异常类，并且可以获取抛出的异常信息，自由度更大。
 */
@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(SQLException.class)
    private RespBean sqlException(SQLException e){
        if (e instanceof SQLIntegrityConstraintViolationException){
            return RespBean.error("该数据有关联数据，操作失败！");
        }
        return RespBean.error("数据库异常，操作失败！");
    }
}
