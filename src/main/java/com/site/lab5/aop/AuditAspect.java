package com.site.lab5.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class AuditAspect {

    private static final Logger log = LoggerFactory.getLogger(AuditAspect.class);

    // args(id) binds the method argument to the parameter named id
    @Before("com.site.lab5.aop.CommonPointcuts.deleteMethods() && args(id)")
    public void auditDelete(JoinPoint joinPoint, Long id) {
        log.warn("[AUDIT] {} requested deletion of actor id={} at {}",
                joinPoint.getSignature().getName(), id, LocalDateTime.now());
    }
}