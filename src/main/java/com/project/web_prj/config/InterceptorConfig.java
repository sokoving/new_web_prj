package com.project.web_prj.config;

import com.project.web_prj.intercepter.AfterLoginInterceptor;
import com.project.web_prj.intercepter.AutoLoginInterceptor;
import com.project.web_prj.intercepter.BoardInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 다양한 여러 인터셉터들을 관리하는 설정 클래스
@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final BoardInterceptor boardInterceptor;
    private final AfterLoginInterceptor afterLoginInterceptor;
    private final AutoLoginInterceptor autoLoginInterceptor;

    // addInterceptors : 인터셉터 설정 추가 메서드
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 게시판 인터셉터 설정
        registry.addInterceptor(boardInterceptor) // 인터셉터 추가 (boardInterceptor: 로그인 안 했으면 false 리턴하는 인터셉터)
                .addPathPatterns("/board/*")        // 경로 패턴 추가
                .excludePathPatterns("/board/list", "/board/content"); // 경로패턴 제외

        // 애프터 로그인 인터셉터 설정
        registry.addInterceptor(afterLoginInterceptor)
                .addPathPatterns("/member/sign-in", "/member/sign-up");
        
        // 자동 로그인 인터셉터 설정
        registry.addInterceptor(autoLoginInterceptor)
                .addPathPatterns("/**"); // 클라이언트 진입점이 어디든 간에 자동로그인 검사
    }
}
