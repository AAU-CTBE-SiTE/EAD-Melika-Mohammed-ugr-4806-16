package com.site.lab5.aop;

import org.aspectj.lang.annotation.Pointcut;

public class CommonPointcuts {

    // every method in the controller package
    @Pointcut("execution(* com.site.lab5.controller..*(..))")
    public void controllerMethods() {}

    // only methods marked with @LogExecutionTime
    @Pointcut("@annotation(com.site.lab5.aop.LogExecutionTime)")
    public void timedMethods() {}

    // delete methods of ActorController
    @Pointcut("execution(* com.site.lab5.controller.ActorController.delete*(..))")
    public void deleteMethods() {}
}