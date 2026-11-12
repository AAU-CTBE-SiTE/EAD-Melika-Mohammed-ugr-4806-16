package com.site.lab5.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(2)
public class TimingAspect {

    private static final Logger log = LoggerFactory.getLogger(TimingAspect.class);

    @Around("com.site.lab5.aop.CommonPointcuts.timedMethods()")
    public Object measureTime(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.nanoTime();
        try {
            return pjp.proceed();   // run the real method
        } finally {
            long ms = (System.nanoTime() - start) / 1_000_000;
            log.info("[TIMING] {}() took {} ms", pjp.getSignature().getName(), ms);
        }
    }
}