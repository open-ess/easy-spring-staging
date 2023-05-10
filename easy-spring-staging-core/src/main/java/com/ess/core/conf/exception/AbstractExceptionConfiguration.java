/**
 * Copyright(C) 2018 Company:easy-spring-staging Co.
 */

package com.ess.core.conf.exception;


import com.ess.core.exception.BusinessException;
import com.ess.core.model.RestStatus;
import com.ess.core.model.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.BindException;


/**
 * 异常处理器 .
 *
 * <p>
 * 功能详细描述
 *
 * @author caobaoyu
 * @date 2018/10/29 14:11
 */
@Slf4j
public abstract class AbstractExceptionConfiguration {
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public RestResult<Void> handler(Exception e) {
        log.error(e.getMessage(), e);
        try {
            if (e instanceof BindException) {
                return RestResult.fail(RestStatus.BAD_REQUEST, null);
            } else if (e instanceof HttpMessageNotReadableException) {
                return RestResult.fail(RestStatus.BAD_REQUEST, null);
            } else if (e instanceof MethodArgumentNotValidException) {
                return RestResult.fail(RestStatus.BAD_REQUEST, null);
            } else if (e instanceof TypeMismatchException) {
                return RestResult.fail(RestStatus.BAD_REQUEST, null);
            } else if (e instanceof ServletRequestBindingException) {
                return RestResult.fail(RestStatus.BAD_REQUEST, null);
            } else if (e instanceof MissingRequestValueException) {
                return RestResult.fail(RestStatus.BAD_REQUEST, null);
            } else if (e instanceof HttpMessageConversionException) {
                return RestResult.fail(RestStatus.BAD_REQUEST, null);
            } else if (e instanceof NoHandlerFoundException) {
                return RestResult.fail(RestStatus.NOT_FOUND, null);
            } else if (e instanceof BusinessException) {
                BusinessException be = (BusinessException) e;
                return RestResult.create(be.getCode(), be.getMessage(), null);
            } else {
                return RestResult.fail(RestStatus.INTERNAL_SERVER_ERROR, null);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return RestResult.fail(RestStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

}
