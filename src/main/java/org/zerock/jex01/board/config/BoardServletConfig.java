package org.zerock.jex01.board.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@ComponentScan(basePackages = "org.zerock.jex01.board.controller")
public class BoardServletConfig implements WebMvcConfigurer {

}
