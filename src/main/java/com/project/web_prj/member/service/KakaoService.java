package com.project.web_prj.member.service;

import com.project.web_prj.member.domain.OAuthValue;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

@Service
@Log4j2
public class KakaoService implements OAuthService, OAuthValue {

    // 카카오 로그인시 사용자 정보에 접근할 수 있는 액세스토큰을 발급
    @Override
    public String getAccessToken(String authCode) throws Exception {

        // 1. 액세스 토큰을 발급 요청할 URI
        String reqUri = "https://kauth.kakao.com/oauth/token";

        // 2. server to server 요청
        // 2-a. 문자타입의 URL을 객체로 포장
        URL url = new URL(reqUri);

        // 2-b. 해당 요청을 연결하고 그 연결정보를 담을 Connection객체 생성
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // 2-c. 요청 정보 설정(HttpURLConnection 전용 메서드 setRequestMethod())
        conn.setRequestMethod("POST"); // 요청 방식 설정

        // 요청 헤더 설정
        conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        conn.setDoOutput(true); // 응답 결과를 받겠다.
        snedAccessTokenRequest(authCode, conn);

        // 3. 응답 데이터 받기
        try(BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {

            // 3-a. 응답 데이터를 입력 스트림으로부터 읽기
            String responseData = br.readLine();
            log.info("responseData - {}", responseData);
            /*
                    responseData는 json으로 온다
                    {"access_token":"WPZFGZKgKFKYSd2boKsvGViNjY5F1q9QypEJtMOeCj10mQAAAYJr0WfR"
                    "token_type":"bearer"
                    ,"refresh_token":"OtoEN6cetbFOYCPXPTu5Q8jLBOWMZhf4NzORqTGaCj10mQAAAYJr0WfQ"
                    ,"expires_in":21599
                    ,"scope":"profile_image profile_nickname"
                    ,"refresh_token_expires_in":5183999}
             */

        } catch (Exception e){
            e.printStackTrace();
        }


        return null;
    }

    // server to server 요청 정보 작성
    private void snedAccessTokenRequest(String authCode, HttpURLConnection conn) throws IOException {


        // 2-d.(버퍼 스트림으로)  요청 파라미터 추가
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()))) {

            StringBuilder queryParam = new StringBuilder();
            queryParam
                    .append("grant_type=authorization_code")
                    .append("&client_id=" + KAKAO_APP_KEY)
                    .append("&redirect_uri=http://localhost:8183" + KAKAO_REDIRECT_URI)
                    .append("&code=" + authCode);

            // 출력스트림을 이용해서 파라미터 전송
            bw.write(queryParam.toString());
            // 출력 버퍼 비우기
            bw.flush();

            // 응답 상태코드 확인
            int responseCode = conn.getResponseCode();
            log.info("response code - {}", responseCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
