package com.site.lab5.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Order(1)
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("com.site.lab5.aop.CommonPointcuts.controllerMethods()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("[BEFORE] {}() called with args {}",
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(
            pointcut = "com.site.lab5.aop.CommonPointcuts.controllerMethods()",
            returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("[AFTER RETURNING] {}() returned {}",
                joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(
            pointcut = "com.site.lab5.aop.CommonPointcuts.controllerMethods()",
            throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        log.error("[AFTER THROWING] {}() threw {}: {}",
                joinPoint.getSignature().getName(),
                ex.getClass().getSimpleName(), ex.getMessage());
    }

    @After("com.site.lab5.aop.CommonPointcuts.controllerMethods()")
    public void logAfter(JoinPoint joinPoint) {
        log.info("[AFTER] {}() finished", joinPoint.getSignature().getName());
    }
}