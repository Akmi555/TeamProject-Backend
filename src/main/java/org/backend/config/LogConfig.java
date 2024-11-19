package org.backend.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class LogConfig {

    @Pointcut("execution (public * org.backend.controller.*.*(..))")
    public void logForController(){}


    @Pointcut("execution (public * org.backend.service.*.*(..))")
    public void logForService(){}

    @Before("logForController()")
    public void beforeUsingAnyController(JoinPoint point){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        log.info("""
                RECEIVED REQUEST:
                IP: {}
                HTTP METHOD: {}
                URL: {}
                """,
                request.getRemoteAddr(),
                request.getMethod(),
                request.getRequestURL().toString());
    }


    @Before("logForService()")
    public void beforeUsingAnyService(JoinPoint point){
        log.info("RUN SERVICE: \n SERVICE METHOD: {}.{}",
                point.getSignature().getDeclaringTypeName(), point.getSignature().getName());
    }

    @AfterThrowing(throwing = "e", pointcut = "logForController()")
    public void throwException(JoinPoint point, Exception e){
        log.error("Request throw an exception. Cause - {}.{}",
                Arrays.toString(point.getArgs()), e.getMessage());
    }

    }