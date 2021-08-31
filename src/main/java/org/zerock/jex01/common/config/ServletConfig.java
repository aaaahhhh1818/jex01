package org.zerock.jex01.common.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.zerock.jex01.common.converter.StringToLocalDateTimeConverter;

@EnableWebMvc //Spring FrameWork에서 자동으로 config값(설정값)을 세팅해준다.
@ComponentScan(basePackages = {"com.example.controller"})//해당 패키지를 스캔하는 어노테이션
public class ServletConfig implements WebMvcConfigurer {

    //스프링관련설정 추가 (LocalDate 관련)
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToLocalDateTimeConverter());
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/views/");
        bean.setSuffix(".jsp");
        registry.viewResolver(bean);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        //("/resources/**") <- webapp밑에 있는 rewources폴더 : 이 경로는 스프링으로 처리하지 않는다.
        // => HTML, JS 파일은 다 여기에 넣어야 한다.
    }
}
