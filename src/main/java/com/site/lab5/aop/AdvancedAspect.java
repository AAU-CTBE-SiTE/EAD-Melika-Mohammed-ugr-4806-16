package com.site.lab5.aop;

import com.site.lab5.model.Actor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AdvancedAspect {

    private static final Logger log = LoggerFactory.getLogger(AdvancedAspect.class);

    // 1. Introduction: gives ActorController the Describable interface
    @DeclareParents(value = "com.site.lab5.controller.ActorController",
            defaultImpl = DescribableImpl.class)
    public static Describable describable;

    // 2. Designators: within + target, and the proxy vs real object
    @Before("within(com.site.lab5.controller.*) && target(controller)")
    public void showTargetAndProxy(JoinPoint jp, Object controller) {
        log.info("[TARGET] real object = {}, proxy = {}",
                controller.getClass().getSimpleName(),
                jp.getThis().getClass().getSimpleName());
    }

    // 3. bean() designator: matches by Spring bean name
    @Before("bean(actorController) && execution(* getAllActors(..))")
    public void beanDesignator() {
        log.info("[BEAN] applied because the bean is named actorController");
    }

    // 4. Read an annotation's attributes through MethodSignature
    @Before("@annotation(audited)")
    public void readAnnotation(JoinPoint jp, Audited audited) {
        MethodSignature sig = (MethodSignature) jp.getSignature();
        log.info("[ANNOTATION] {} action={} returns {}",
                sig.getMethod().getName(), audited.action(),
                sig.getReturnType().getSimpleName());
    }

    // 5. @Around that modifies the argument before proceeding
    @Around("execution(* com.site.lab5.controller.ActorController.createActor(..)) && args(actor)")
    public Object cleanName(ProceedingJoinPoint pjp, Actor actor) throws Throwable {
        if (actor.getName() != null) {
            actor.setName(actor.getName().trim());
        }
        Object result = pjp.proceed(new Object[]{actor});
        log.info("[AROUND] name trimmed to '{}'", actor.getName());
        return result;
    }
}