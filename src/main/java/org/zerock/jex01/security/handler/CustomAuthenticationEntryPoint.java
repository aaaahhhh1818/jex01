package org.zerock.jex01.security.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) //인증 exception 파라미터로 보낼꺼임
            throws IOException, ServletException {

        log.error("------------------CustomAuthenticationEntryPoint-----------------");
        log.error("------------------CustomAuthenticationEntryPoint-----------------");
        log.error(authException);
        log.error("------------------CustomAuthenticationEntryPoint-----------------");
        log.error("------------------CustomAuthenticationEntryPoint-----------------");

        if(req.getContentType() != null && req.getContentType().contains("json")){
            res.setContentType("application/json;charset=UTF-8");
            res.setStatus(403);
            res.getWriter().write("{\'msg\':\'REST API ERROR\'}");
        }else {

            res.sendRedirect("/customLogin?erzzzzzzs");
        }



    }
}