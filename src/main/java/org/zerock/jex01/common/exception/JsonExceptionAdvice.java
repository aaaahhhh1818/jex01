//package org.zerock.jex01.common.exception;
//
//import lombok.extern.log4j.Log4j2;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import javax.servlet.http.HttpServletRequest;
//
//@RestControllerAdvice
//@Log4j2
//public class JsonExceptionAdvice {
//
//    @ExceptionHandler({Exception.class})
//    public ResponseEntity<String> exceptionHandler(HttpServletRequest request) {
//
//        log.error("=======================================");
//        log.error("=======================================");
//        log.error("=======================================");
//        log.error("=======================================");
//        log.error("=======================================");
//        //e.printStackTrace();
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("ACCESS ERROR");
//    }
//
//}
