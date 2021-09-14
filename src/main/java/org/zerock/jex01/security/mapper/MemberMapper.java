package org.zerock.jex01.security.mapper;

import org.zerock.jex01.security.domain.Member;

public interface MemberMapper {

    public Member findByMid(String mid);

}
