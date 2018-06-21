package com.biboheart.huip.user;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpStatus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.biboheart.brick.exception.BhException;
import com.biboheart.brick.model.BhResponseResult;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@RestControllerAdvice
@Slf4j
public class UserApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}
	
	@ExceptionHandler(value = Exception.class)
	public BhResponseResult<?> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
			throw e;
		}
		if (e instanceof AuthenticationException) {
			throw e;
		}
		if (e instanceof BhException) {
			return new BhResponseResult<>(HttpStatus.SC_INTERNAL_SERVER_ERROR, e.getMessage(), null);
		} else {
			log.error("Request path : [url:{}, method:{}] 发生异常 => [异常类：{}, 异常信息:{}]", req.getRequestURI(),
					req.getMethod(), e.getClass(), e.getMessage());
			return new BhResponseResult<>(HttpStatus.SC_INTERNAL_SERVER_ERROR, "系统操作异常", e.getMessage());
		}
	}
}
