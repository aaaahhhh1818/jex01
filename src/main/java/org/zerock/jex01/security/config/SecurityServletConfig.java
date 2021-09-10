package org.zerock.jex01.security.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"org.zerock.jex01.security.controller"})
public class SecurityServletConfig {
}
