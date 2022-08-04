package com.project.web_prj.member.domain;

import lombok.*;


import java.util.Date;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    private String account;
    private String password;
    private String name;
    private String email;
    private Auth auth;
    private Date regDate;      // 모든 테이블에 들어가는 도메인의 공통 필드는 상속을 통해 해결하면 좋음
    private String sessionId;
    private Date limitTime;

}
