package com.project.web_prj.util;

import javax.servlet.http.HttpSession;

public class LoginUtils {

    public static final String LOGIN_FLAG = "loginUser";

    // 로그인했는지 알려주기(리팩토링 팁: 자주 쓰는 코드를 static 메서드로 만들기)
    public static boolean isLogin(HttpSession session) {
        return session.getAttribute(LOGIN_FLAG) != null;
    }
}
