package org.zerock.jex01.board.security;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.jex01.common.config.RootConfig;
import org.zerock.jex01.security.config.SecurityConfig;
import org.zerock.jex01.security.domain.Member;
import org.zerock.jex01.security.mapper.MemberMapper;

@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration( classes = {RootConfig.class, SecurityConfig.class})
public class PasswordTests {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired(required = false) //에러나지마 ! 지금은 disable 해놔서 에러안남
    MemberMapper memberMapper;

    @Test
    public void testMember() {
        String mid = "admin0";

        Member member = memberMapper.findByMid(mid);

        log.warn("---------------------");
        log.warn(member);
    }

    @Test //접은 암호 확인
    public void testEncode() {
        String str = "member1";
        String enStr = passwordEncoder.encode(str);
        log.warn(enStr);
    }

    @Test //제대로 접히는지 확인
    public void testDecode() {
        String str = "$2a$10$Y1eIrASYOdcu89KxuLsfDuYyhSt2dJv7JT22.Fvhp2ge3EfaKUSsu";

        boolean match = passwordEncoder.matches("member1", str); //패스워드가 맞는지 아닌지 확인해주는거

        log.warn(match);

    }

    @Test //쿼리문 생성
    public void inserMember() {

        //insert into tbl_member (mid,mpw,mname) values ('mid','mpw','mname'); //pw encoding 된 값 넣어서 if문 돌리기

        String query = "insert into tbl_member (mid,mpw,mname) values ('v1','v2','v3');";

        for(int i = 0; i < 10; i++) {

            String mid = "user" + i; //user0
            String mpw = passwordEncoder.encode("pw"+i); //pw0 -> Bcrypt
            String mname = "유저" + i; //유저0

            String result = query;

            result = result.replace("v1", mid);
            result = result.replace("v2", mpw);
            result = result.replace("v3", mname);

            System.out.println(result);

        }

    }

    @Test //쿼리문 생성
    public void inserAdmin() {

        //insert into tbl_member (mid,mpw,mname) values ('mid','mpw','mname'); //pw encoding 된 값 넣어서 if문 돌리기

        String query = "insert into tbl_member (mid,mpw,mname) values ('v1','v2','v3');";

        for(int i = 0; i < 10; i++) {

            String mid = "admin" + i; //user0
            String mpw = passwordEncoder.encode("pw"+i); //pw0 -> Bcypted
            String mname = "관리자" + i; //유저0

            String result = query;

            result = result.replace("v1", mid);
            result = result.replace("v2", mpw);
            result = result.replace("v3", mname);

            System.out.println(result);

        }

    }

    @Test
    public void insertMemberRole() {

        //문자열 replace 시키지 않고 formatting
        String sql = "insert into tbl_member_role (mid, role) values ('%s', '%s');";

        for(int i = 0; i < 10; i++) {

            String result = String.format(sql, "user" + i, "ROLE_MEMBER");

            System.out.println(result);

        }

    }

    @Test
    public void insertAdminRole() {

        //문자열 replace 시키지 않고 formatting
        String sql = "insert into tbl_member_role (mid, role) values ('%s', '%s');";

        for(int i = 0; i < 10; i++) {

            String result = String.format(sql, "admin" + i, "ROLE_MEMBER");

            System.out.println(result);

            String result2 = String.format(sql, "admin" + i, "ROLE_ADMIN");

            System.out.println(result2);

        }

    }

}
