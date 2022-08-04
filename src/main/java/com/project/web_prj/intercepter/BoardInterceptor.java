package com.project.web_prj.intercepter;

import com.project.web_prj.board.domain.Board;
import com.project.web_prj.board.dto.ValidateMemberDTO;
import com.project.web_prj.member.domain.Auth;
import com.project.web_prj.util.FileUtils;
import com.project.web_prj.util.LoginUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.project.web_prj.util.LoginUtils.*;

// 인터셉터: 컨트롤러에 요청이 들어가기 전, 후에
//          공통 처리할 일들을 정의해놓는 클래스
@Configuration
@Log4j2
public class BoardInterceptor implements HandlerInterceptor {

    /*
        preHandle
         - 인터셉터의 전처리 메서드
         리턴값이 true일 경우 컨트롤러 집입을 허용하고
         false일 경우 진입을 허용하지 않는다
     */

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        // HttpServletRequest 객체 이용해 포워딩하기
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/member/sign-in.jsp");

        log.info("board interceptor preHandle()");
        if (!isLogin(session)) {
            log.info("this request deny!! 집에 가");
            // dispatcher.forward(request, response);

            // 로그인 안 한 유저 로그인 페이지로 리다이렉트
            response.sendRedirect("/member/sign-in?message=no-login");
            return false;
        }
        return true;
    }

    /*
        postHandle
        - 후처리 메서드
         - preHandle 통과하고(true return) 컨트롤러를 호출한 뒤 무조건 호출된다
        문제 1. 로그인이 돼어 있는 계정은 preHandle에서 통과가 되기 때문에
                프론트에서 막아도 url을 직접 입력하면 modify에 진입 가능하다
         해결 1. 보드의 account와 현재 로그인한 account를 비교해서 다르면 list로 리다이렉트

        문제 2. 마찬가지로 preHandle을 통과하는 write에서도 postHandle 인터셉트,
            board가 없기 때문에 nullPointExeception 발생
         해결 2. postHandle의 작동 범위를 제한시킨다

        문제 3. 관리자는 수정을 못 한다

 */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        // postHandle이 작동해야 하는 URI 목록
        List<String> uriList = Arrays.asList("/board/modify", "/board/delete");

        // 현재 요청 URI 정보 알아내기
        String requestURI = request.getRequestURI();
        log.info("requestURI - {}", requestURI);

        // 현재 요청 메서드 정보 확인
        String method = request.getMethod();

        // postHandle은 uriList 목록에 있는 URI에서만 작동하게 함
        if (uriList.contains(requestURI) && method.equalsIgnoreCase("GET")) {
            log.info("board interceptor postHandle() ! ");

            HttpSession session = request.getSession();

            // 컨트롤러의 메서드를 처리한 후 모델에 담긴 데이터의 맵
            Map<String, Object> modelMap = modelAndView.getModel();

//        log.info("modelMap.size() - {}", modelMap.size());
//            log.info("modelMap - {}", modelMap);

            ValidateMemberDTO dto = (ValidateMemberDTO) modelMap.get("validate");


            // 수정하려는 게시글의 계정명 정보와 세션에 저장된 계정명 정보가
            // 일치하지 않으면 돌려보내라
//            log.info("게시물의 계정명 - {}", dto.getAccount());
//            log.info("로그인한 계정명 - {}", getCurrentMemberAccount(request.getSession()));

            if (isAdmin(session)) return;

            if (!isMine(session, dto.getAccount())) {
                response.sendRedirect("/board/list");
            }
        }
    }

    private boolean isAdmin(HttpSession session) {
        return getCurrentMemberAuth(session).equals("ADMIN");
    }

    private static boolean isMine(HttpSession session, String account) {
        return account.equals(getCurrentMemberAccount(session));
    }
}