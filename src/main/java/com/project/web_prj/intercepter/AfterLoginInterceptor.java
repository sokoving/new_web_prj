package com.project.web_prj.intercepter;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.project.web_prj.util.LoginUtils.isLogin;

@Configuration
@Log4j2
public class AfterLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        // 로그인 유저가 로그인을 시도햇을 때 인덱스로 리다이렉트
        if(isLogin(session)){
            response.sendRedirect("/");
            return false;
        }
        return true;
    }
}
