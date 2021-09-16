package org.zerock.jex01.security.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.zerock.jex01.security.domain.Member;

import java.util.Collection;
import java.util.stream.Collectors;


@Getter
@Setter
@ToString
public class MemberDTO extends User {

    private String mid;
    private String mpw;
    private String mname;
    private boolean enabled;

    public MemberDTO(Member member){
        super(member.getMid(),
                member.getMpw(),
                member.getRoleList().stream()
                        .map(memberRole ->
                                new SimpleGrantedAuthority(memberRole.getRole())).collect(Collectors.toList())  );
        this.mid = member.getMid();
        this.mpw = member.getMpw();
        this.mname = member.getMname();
        this.enabled = member.isEnabled();
    }

}