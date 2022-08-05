package com.project.web_prj.member.service;

// SNS 로그인 통신 처리 담당(구글, 네이버, 카카오 통합)
public interface OAuthService {

    /**
     * 액세스토큰 발급 메서드
     * @param authCode - 인증서버가 발급한 인가코드
     * @return - 액세스 토큰
     * @throws Exception - 통신 예러
     */
    String getAccessToken(String authCode) throws Exception;
}
