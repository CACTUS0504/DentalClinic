package org.example.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@org.aspectj.lang.annotation.Aspect
public class Aspect {
    @Before("allServiceMethods()")
    public void logParameters(JoinPoint joinPoint) {
        log.info("Executing operation:" + joinPoint.getSignature().toShortString() + "Parameters: {}", joinPoint.getArgs());
    }
    @Pointcut("within(org.example.service.*)")
    public void allServiceMethods() {}

    @Around("executionServiceMethod()")
    public Object timeExecutionServiceMethod(ProceedingJoinPoint jp) throws Throwable {
        long begin = System.currentTimeMillis();
        Object object = jp.proceed();
        long end = System.currentTimeMillis();
        log.info(jp.getSignature().toShortString() + " completed in " + (end - begin) + " ms");
        return object;
    }

    @Pointcut("within(org.example.service.*)")
    public void executionServiceMethod() {}
}
