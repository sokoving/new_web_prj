package com.project.web_prj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 시큐리티 설정을 웹에 적용
public class SecurityConfig {

    // 시큐리티 기본 설정을 처리하는 빈
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        /*
            초기에 나오는 디폴트 로그인 화면 안뜨게 하기
            csrf().disable() : csrf 공격 방어토큰 자동 생성 해제
            authorizeRequests() : 권한 요청 범위 설정
         */
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/member/**")
                .permitAll();

        return http.build();
    }

}

