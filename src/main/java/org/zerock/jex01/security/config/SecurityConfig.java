package org.zerock.jex01.security.config;

import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.zerock.jex01.security.handler.CustomLoginSuccessHandler;
import org.zerock.jex01.security.service.CustomUserDetailsService;

import javax.sql.DataSource;

@Configuration //설정파일이야
@EnableWebSecurity //시큐리티를 접목하는거다
@Log4j2
@MapperScan(basePackages = "org.zerock.jex01.security.mapper")
@ComponentScan(basePackages = "org.zerock.jex01.security.service")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    //db 주입받기
    @Autowired
    private DataSource dataSource;

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

        http.rememberMe().tokenRepository(persistentTokenRepository())
                .key("zerock").tokenValiditySeconds(60*60*24*30); //한달 동안 유지됨

    }

    @Bean //handler 객체 생성
    public CustomLoginSuccessHandler customLoginSuccessHandler() {
        return new CustomLoginSuccessHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //메모리 상에서 설정하는 부분
//        auth.inMemoryAuthentication().withUser("member1").password("$2a$10$Y1eIrASYOdcu89KxuLsfDuYyhSt2dJv7JT22.Fvhp2ge3EfaKUSsu")
//                .roles("MEMBER");
//        auth.inMemoryAuthentication().withUser("admin1").password("$2a$10$Y1eIrASYOdcu89KxuLsfDuYyhSt2dJv7JT22.Fvhp2ge3EfaKUSsu")
//                .roles("MEMBER","ADMIN");

        //auth.userDetailsService(customUserDetailsService()); //얘를 통해서 login process 진행하겠다 (프로그램상에서 설정)

        auth.userDetailsService(customUserDetailsService);

    }

//    @Bean
//    public CustomUserDetailsService customUserDetailsService() {
//        return new CustomUserDetailsService();
//    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {

        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        return repository;

    }

}
