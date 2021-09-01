package org.zerock.jex01.common.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@Log4j2
public class CommonExceptionAdvice {

    @ExceptionHandler(Exception.class) //어떤 종류의 에러가 나면 이걸 발생시킬거야 라고 알려주는거
    public String exceptAll(Exception ex, Model model) {

        log.error(ex.getMessage());

        model.addAttribute("exception", ex); //모델에 exception 담아줌

        return "error_page";
    }

    //ArithmeticException

    @ExceptionHandler(ArithmeticException.class)
    public String exceptArithmetic(ArithmeticException ex, Model model){

        log.info("exceptArithmetic");
        log.error(ex.getClass().getName());
        log.error(ex.getMessage());

        model.addAttribute("exception", ex);

        return "error_arithmetic_page";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) //상수 줌
    public String handle404(NoHandlerFoundException ex) {
        return "custom404";
    }

}
