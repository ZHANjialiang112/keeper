package com.zjl.keeper.core.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class TestLogAspect {

    @Around("@annotation(com.zjl.keeper.core.annotation.TestLog)")
    public Object testLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Around start测试注解");
        String proceed = (String) joinPoint.proceed();
        log.info("Around end测试注解");
        log.info("测试注解返回值：{}",proceed);
        return proceed;
    }

    @Before("@annotation(com.zjl.keeper.core.annotation.TestLog)")
    public void testLogBefore()
    {
        log.info("Before测试注解");
    }

    @After("@annotation(com.zjl.keeper.core.annotation.TestLog)")
        public void testLogAfter()
    {
        log.info("After测试注解");
    }
}
