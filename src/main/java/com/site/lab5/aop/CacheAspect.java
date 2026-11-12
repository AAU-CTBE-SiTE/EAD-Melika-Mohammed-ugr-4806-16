package com.site.lab5.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
@Order(1)   // runs outside TimingAspect (Order 2), so a HIT skips the timer
public class CacheAspect {

    private static final Logger log = LoggerFactory.getLogger(CacheAspect.class);
    private final Map<String, Object> cache = new ConcurrentHashMap<>();

    @Around("@annotation(com.site.lab5.aop.SimpleCache)")
    public Object cacheResult(ProceedingJoinPoint pjp) throws Throwable {
        String key = pjp.getSignature().toShortString() + Arrays.toString(pjp.getArgs());

        if (cache.containsKey(key)) {
            log.info("[CACHE] HIT {}", key);
            return cache.get(key);          // proceed() is NOT called
        }

        log.info("[CACHE] MISS {}", key);
        Object result = pjp.proceed();      // run the real method
        cache.put(key, result);
        return result;
    }

    // Clear the cache whenever the data changes
    @AfterReturning("execution(* com.site.lab5.service.ActorService.add*(..)) "
            + "|| execution(* com.site.lab5.service.ActorService.deleteById(..))")
    public void clearCache() {
        cache.clear();
        log.info("[CACHE] cleared because data changed");
    }
}