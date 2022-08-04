package com.project.web_prj.intercepter;

import com.project.web_prj.util.LoginUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.project.web_prj.util.LoginUtils.isLogin;

// 인터셉터: 컨트롤러에 요청이 들어가기 전, 후에
//          공통 처리할 일들을 정의해놓는 클래스
@Configuration
@Log4j2
public class BoardInterceptor implements HandlerInterceptor {

    /*
        preHandle
         -  인터셉터의 전처리 메서드
         리턴값이 true일 경우 컨트롤러 집입을 허용하고
         false일 경우 진입을 허용하지 않는다
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        // HttpServletRequest 객체 이용해 포워딩하기
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/member/sign-in.jsp");

        log.info("board interceptor preHandle() call");
        if(!isLogin(session)){
            log.info("board interceptor preHandle() - this request deny !! 집에 가!");
//            dispatcher.forward(request, response);

            // 로그인 안 한 유저 로그인 페이지로 리다이렉트
            response.sendRedirect("/member/sign-in?message=no-login");
            return false;
        }
        return true;
    }

    /*
        postHandle
         - preHandle 통과하고(true return) 컨트롤러를 호출한 뒤 호출된다
 */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("board interceptor postHandle() call!!");
    }
}
