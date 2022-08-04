package com.project.web_prj.util;

import com.project.web_prj.member.domain.Member;
import com.project.web_prj.member.service.LoginFlag;

import javax.servlet.http.HttpSession;

public class LoginUtils {

    public static final String LOGIN_FLAG = "loginUser";

    // 로그인했는지 알려주기(리팩토링 팁: 자주 쓰는 코드를 static 메서드로 만들기)
    public static boolean isLogin(HttpSession session) {
        return session.getAttribute(LOGIN_FLAG) != null;
    }

    // 로그인 한 사용자 계정 가져오기
    public static String getCurrentMemberAccount(HttpSession session) {
        Member member = (Member) session.getAttribute(LOGIN_FLAG);
        return member.getAccount();
    }

    // 로그인 한 사용자 권한 가져오기
    public static String getCurrentMemberAuth(HttpSession session) {
        Member member = (Member) session.getAttribute(LOGIN_FLAG);
        return member.getAuth().toString();
    }
}