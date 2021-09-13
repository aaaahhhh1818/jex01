package org.zerock.jex01.security.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zerock.jex01.security.handler.CustomLoginSuccessHandler;
import org.zerock.jex01.security.service.CustomUserDetailsService;

@Configuration //설정파일이야
@EnableWebSecurity //시큐리티를 접목하는거다
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests() //인가받은 사용자만 쓰는데 ..그중에 어떤?? ↓↓↓↓
                .antMatchers("/sample/doAll").permitAll()
                .antMatchers("/sample/doMember").access("hasRole('ROLE_MEMBER')")
                .antMatchers("/sample/doAdmin").access("hasRole('ROLE_ADMIN')");

        http.formLogin().loginPage("/customLogin") //로그인 페이지를 보여주는 역할 (spring security에서 보여주는 자동 기능)
                .loginProcessingUrl("/login"); //로그인 화면만 custom 사용하고 실제 기능은 security가 하게 함!
//                .successHandler(customLoginSuccessHandler()); //handler이거 사용해준다고 알려주는거

//        http.logout().invalidateHttpSession(true); //default 값이라 안줘도됨

        http.csrf().disable(); //security 설정에 csrf를 쓰지 않겠다고 해주는 것

    }

    @Bean //handler 객체 생성
    public CustomLoginSuccessHandler customLoginSuccessHandler() {
        return new CustomLoginSuccessHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

//        auth.inMemoryAuthentication().withUser("member1").password("$2a$10$Y1eIrASYOdcu89KxuLsfDuYyhSt2dJv7JT22.Fvhp2ge3EfaKUSsu")
//                .roles("MEMBER");
//        auth.inMemoryAuthentication().withUser("admin1").password("$2a$10$Y1eIrASYOdcu89KxuLsfDuYyhSt2dJv7JT22.Fvhp2ge3EfaKUSsu")
//                .roles("MEMBER","ADMIN");

        auth.userDetailsService(customUserDetailsService()); //얘를 통해서 login process 진행하겠다

    }

    @Bean
    public CustomUserDetailsService customUserDetailsService() {
        return new CustomUserDetailsService();
    }
}
