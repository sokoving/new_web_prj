package com.project.web_prj.member.dto;

import lombok.*;

@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
public class KakaoUserInfoDTO {

    private String nickName;
    private String profileImg;
    private String email;
    private String gender;

}
