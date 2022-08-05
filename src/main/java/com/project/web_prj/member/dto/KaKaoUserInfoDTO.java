package com.project.web_prj.member.dto;

import lombok.*;

@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
public class KaKaoUserInfoDTO {

    private String nickName;
    private String profileImage;
    private String email;
    private String gender;

}
