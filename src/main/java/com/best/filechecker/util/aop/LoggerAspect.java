package com.best.filechecker.util.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@Component
@Aspect
public class LoggerAspect {

    @Before("execution(* *..*.* (..))")
    public void logExecutedMethod(JoinPoint joinPoint){
        log.debug("executed method: {}", joinPoint.getSignature().getName());
    }

    @Around("@annotation(TimeLogger)")
    public void logMethodExecutionTime(ProceedingJoinPoint joinPoint){
        long start = Instant.now().toEpochMilli();
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("Error while proceeding", throwable);
        }
      long end = Instant.now().toEpochMilli();
      long duration = Duration.ofMillis(end - start).toSeconds();
      log.info("Execution took {} seconds", duration);
    }

}
