package org.zerock.jex01.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.jex01.security.domain.Member;
import org.zerock.jex01.security.dto.MemberDTO;
import org.zerock.jex01.security.mapper.MemberMapper;

import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //pw틀리면 id는 존재하는데 권한이 없다고??팅김???

        log.warn("CustomUserDetailsService------------------------------------");
        log.warn("CustomUserDetailsService------------------------------------");
        log.warn(username);
        log.warn(memberMapper);
        log.warn("CustomUserDetailsService------------------------------------");
        log.warn("CustomUserDetailsService------------------------------------");

        Member member = memberMapper.findByMid(username);

        if(member == null) {
            throw new UsernameNotFoundException("NOT EXIST"); //예외 -> 리턴대신 사용하는 너낌
        }

        //mapper로 연결해서 가져온 db 정도를 문자열 배열로 바꿔주는 것
        String[] authorities = member.getRoleList().stream().map(memberRole -> memberRole.getRole()).toArray(String[]::new);

        User result = new MemberDTO(member); //회원정보 한번에 받아서 처리
//        User result = (User) User.builder() //다운캐스팅
//                .username(username)
//                .password(member.getMpw())
//                .accountExpired(false) //만료된계정
//                .accountLocked(false) //잠긴계정
//                .authorities(authorities) //계정에 권한주기
//                .build();

        return result;
    }
}
