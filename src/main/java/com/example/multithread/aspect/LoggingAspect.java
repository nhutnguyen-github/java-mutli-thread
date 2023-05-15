package com.example.multithread.aspect;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LoggingAspect {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    
    @Pointcut("within(com.example.multithread.controllers..*)" +
            " || within(com.example.multithread.services..*)")
    public void applicationPackagePointcut() {}

    // execution(* com.make.status.*.*.*(..))
    /**
     * Advice that logs methods throwing exceptions.
     *
     * @param joinPoint join point for advice
     * @param e exception
     */
    @AfterThrowing(pointcut = "applicationPackagePointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        System.out.println(String.format("Exception: %s.%s() |message: %s |cause: %s", 
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(), 
            e.getMessage(), 
            e.getCause() != null ? e.getCause() : "NULL"));
    }

    /**
     * Advice that logs when a method is entered and exited.
     *
     * @param joinPoint join point for advice
     * @return result
     * @throws Throwable throws IllegalArgumentException
     */
    @Around("applicationPackagePointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Map<String, Object> parameters = getParameters(joinPoint);
        System.out.println(String.format("[%s] Enter: %s.%s() |params: %s", 
            Thread.currentThread().getName(),
            joinPoint.getSignature().getDeclaringType(),
            joinPoint.getSignature().getName(), 
            parameters.toString()));
            
        try {
            Object result = joinPoint.proceed();
        
            System.out.println(String.format("[%s] Exit: %s.%s() |result: %s", 
                Thread.currentThread().getName(),
                joinPoint.getSignature().getDeclaringType(),
                joinPoint.getSignature().getName(), result));

            System.out.println(" ... Number: " + threadPoolTaskExecutor.getPoolSize() + ", Queue: " + threadPoolTaskExecutor.getQueueSize() + " ... ");
       
            return result;
        } catch (IllegalArgumentException e) {
            System.out.println(String.format("AOP IllegalArgument: %s in %s.%s()", 
                Arrays.toString(joinPoint.getArgs()),
                joinPoint.getSignature().getDeclaringType(), 
                joinPoint.getSignature().getName()));
            throw e;
        }
    }

    private Map<String, Object> getParameters(JoinPoint joinPoint) {
        HashMap<String, Object> map = new HashMap<>();
        
        CodeSignature signature = (CodeSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();

        for (int i = 0; i < parameterNames.length; i++) {
            map.put(parameterNames[i], joinPoint.getArgs()[i]);
        }

        return map;
    }
}
