package com.project.web_prj.member.service;

import com.project.web_prj.member.domain.Auth;
import com.project.web_prj.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired MemberService service;

    @Test
    @DisplayName("평문 비밀번호로 회원가입하면 암호화되어 저장된다")
    void signUpTest(){
        Member m = new Member();
        m.setAccount("banana");
        m.setPassword("bbb1234");
        m.setName("돌바나나");
        m.setEmail("banana@nana.com");
        m.setAuth(Auth.COMMON);

        service.signUp(m);
    }

}