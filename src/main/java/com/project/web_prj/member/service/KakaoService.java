package com.project.web_prj.member.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.project.web_prj.member.domain.OAuthValue;
import com.project.web_prj.member.dto.KaKaoUserInfoDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


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
        sendAccessTokenRequest(authCode, conn);

        // 3. 응답 데이터 받기
        try (BufferedReader br
                     = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {

            // 3-a. 응답데이터를 입력스트림으로부터 읽기
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
            // 3-b. 응답받은 json 데이터를 gson 라이브러리를 사용하여 자바 객체로 파싱
            JsonParser parser = new JsonParser();
            // JsonElement는 자바로 변환된 JSON
            JsonElement element = parser.parse(responseData);

            // 3-c. json 프로퍼티 키를 사용해서 필요한 데이터 추출
            // (getAsJsonArray() > [] / getAsJsonObject() > {})
            JsonObject object = element.getAsJsonObject();
            String accessToken = object.get("access_token").getAsString();
            String tokenType = object.get("token_type").getAsString();

            log.info("accessToken - {}", accessToken);
            log.info("tokenType - {}", tokenType);

            return accessToken;

        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    // server to server 요청 정보 작성
    private static void sendAccessTokenRequest(String authCode, HttpURLConnection conn) throws IOException {


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


    // 액세스 토큰을 통해 사용자 정보 요청(프로필사진, 닉네임 등)
    public KaKaoUserInfoDTO getKakaoUserInfo(String accessToken) throws Exception {

        String reqUri = "https://kapi.kakao.com/v2/user/me";

        URL url = new URL(reqUri);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");

        conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        conn.setDoOutput(true);

        int responseCode = conn.getResponseCode();
        log.info("userInfo res-code - {}", responseCode);

        //  응답 데이터 받기
        try (BufferedReader br
                     = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {

            String responseData = br.readLine();
            log.info("responseData - {}", responseData);
            /*
            {
                    "id": 2375373387,
                    "connected_at": "2022-08-05T02:14:43Z",
                    "properties": {
                        "nickname": "김봉만",
                        "profile_image": "http://k.kakaocdn.net/dn/bvHw7F/btrITnk1wfP/ljDz74dvKm8khpQQLCP9Lk/img_640x640.jpg",
                        "thumbnail_image": "http://k.kakaocdn.net/dn/bvHw7F/btrITnk1wfP/ljDz74dvKm8khpQQLCP9Lk/img_110x110.jpg"
                    },
                    "kakao_account": {
                        "profile_nickname_needs_agreement": false,
                        "profile_image_needs_agreement": false,
                        "profile": {
                            "nickname": "김봉만",
                            "thumbnail_image_url": "http://k.kakaocdn.net/dn/bvHw7F/btrITnk1wfP/ljDz74dvKm8khpQQLCP9Lk/img_110x110.jpg",
                            "profile_image_url": "http://k.kakaocdn.net/dn/bvHw7F/btrITnk1wfP/ljDz74dvKm8khpQQLCP9Lk/img_640x640.jpg",
                            "is_default_image": false
                        },
                        "has_email": true,
                        "email_needs_agreement": true,
                        "has_gender": true,
                        "gender_needs_agreement": true
                    }
                }
             */

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(responseData);

            JsonObject object = element.getAsJsonObject();

            // 받은 json 데이터에서 원하는 값 뽑기
            JsonObject kakaoAccount = object.get("kakao_account").getAsJsonObject();

            JsonObject profile = kakaoAccount.get("profile").getAsJsonObject();

            String nickName = profile.get("nickname").getAsString();
            String profileImage = profile.get("profile_image_url").getAsString();

//            if (kakaoAccount.get("email_needs_agreement"))
//            String email = kakaoAccount.get("email").getAsString();
            String email = "sokoving@gmail.com";
//            String gender = kakaoAccount.get("gender").getAsString();
            String gender = "female";

            KaKaoUserInfoDTO dto = new KaKaoUserInfoDTO(nickName, profileImage, email, gender);
            dto.setNickName(nickName);
            dto.setProfileImage(profileImage);

            log.info("카카오 유저 정보: {}", dto);

            return dto;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 카카오 로그아웃
        // REST API - 로그아웃 :
        // REST API - 카카오 계정과 함께 로그아웃 :
    public KaKaoUserInfoDTO logout(String accessToken) throws Exception {

        String reqUri = "https://kapi.kakao.com/v1/user/logout";

        URL url = new URL(reqUri);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");

        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        conn.setDoOutput(true);

        int responseCode = conn.getResponseCode();
        log.info("userInfo res-code - {}", responseCode);

        //  응답 데이터 받기
        try (BufferedReader br
                     = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {

            String responseData = br.readLine();
            log.info("responseData - {}", responseData);


            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(responseData);

            JsonObject object = element.getAsJsonObject();

            // 받은 json 데이터에서 원하는 값 뽑기
            JsonObject kakaoAccount = object.get("kakao_account").getAsJsonObject();

            JsonObject profile = kakaoAccount.get("profile").getAsJsonObject();

            String nickName = profile.get("nickname").getAsString();
            String profileImage = profile.get("profile_image_url").getAsString();

//            if (kakaoAccount.get("email_needs_agreement"))
//            String email = kakaoAccount.get("email").getAsString();
            String email = "sokoving@gmail.com";
//            String gender = kakaoAccount.get("gender").getAsString();
            String gender = "female";

            KaKaoUserInfoDTO dto = new KaKaoUserInfoDTO(nickName, profileImage, email, gender);
            dto.setNickName(nickName);
            dto.setProfileImage(profileImage);

            log.info("카카오 유저 정보: {}", dto);

            return dto;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }
}