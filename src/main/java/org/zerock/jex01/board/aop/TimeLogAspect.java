package org.zerock.jex01.board.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Log4j2
@Component //객체로 등록
public class TimeLogAspect {

    { //defalut 블럭 만드는 이유 : 객체가 생성될 때 바로 실행되게 만들기 위해서
        log.info("TimeLogAspect...................");
        log.info("TimeLogAspect...................");
        log.info("TimeLogAspect...................");
        log.info("TimeLogAspect...................");
        log.info("TimeLogAspect...................");
    }

    @Before("execution(* org.zerock.jex01.board.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {

        log.info("logBefore....................");

        log.info(joinPoint.getTarget()); //실제 객체 찍어줌
        log.info(Arrays.toString(joinPoint.getArgs())); //파라미터를 Args로 찍어줄 수 있음

        log.info("logBefore....................END");

    }

    @AfterReturning("execution(* org.zerock.jex01.board.service.*.*(..))")
    public void logAfter() {

        log.info("logAfter....................");

    }

}
