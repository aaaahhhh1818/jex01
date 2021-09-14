package org.zerock.jex01.security.domain;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member {

    private String mid;
    private String mpw;
    private String mname;
    private boolean enabled;

    private List<MemberRole> roleList;
}
