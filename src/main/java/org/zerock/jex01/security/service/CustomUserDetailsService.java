package org.zerock.jex01.security.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Log4j2
public class CustomUserDetailsService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //pw틀리면 id는 존재하는데 권한이 없다고??팅김???

        log.warn("CustomUserDetailsService------------------------------------");
        log.warn("CustomUserDetailsService------------------------------------");
        log.warn(username);
        log.warn("CustomUserDetailsService------------------------------------");
        log.warn("CustomUserDetailsService------------------------------------");

        User result = (User) User.builder() //다운캐스팅
                .username(username)
                .password("$2a$10$Y1eIrASYOdcu89KxuLsfDuYyhSt2dJv7JT22.Fvhp2ge3EfaKUSsu")
                .accountExpired(false) //만료된계정
                .accountLocked(false) //잠긴계정
                .authorities("ROLE_MEMBER", "ROLE_ADMIN") //계정에 권한주기
                .build();

        return result;
    }
}
