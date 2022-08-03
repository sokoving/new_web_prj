package com.project.web_prj.member.controller;

import com.project.web_prj.member.DTO.LoginDTO;
import com.project.web_prj.member.domain.Member;
import com.project.web_prj.member.service.LoginFlag;
import com.project.web_prj.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    // 회원가입 양식 띄우기 요청
    @GetMapping("/sign-up")
    public void signUp() {
        log.info("/member/sign-up GET! - forwarding to sign-up.jsp");
    }

    // 회원가입 처리 요청
    @PostMapping("/sign-up")
    public String signUp(Member member, RedirectAttributes ra) {
        log.info("/member/sign-up POST ! - {}", member);
        boolean flag = memberService.signUp(member);
        ra.addFlashAttribute("msg", "reg-success");
        return flag ? "redirect:/member/sign-in" : "redirect:/member/sign-up";
    }

    // 아이디, 이메일 중복확인 비동기 요청 처리
    @GetMapping("/check")
    @ResponseBody
    public ResponseEntity<Boolean> check(String type, String value) {
        log.info("/member/check?type={}&value={} GET!! ASYNC", type, value);
        boolean flag = memberService.checkSignUpValue(type, value);

        return new ResponseEntity<>(flag, HttpStatus.OK);
    }

    // 로그인 화면을 열어주는 요청 처리
    @GetMapping("/sign-in")
    public void signIn() {
        log.info("/member/sign-in GET! - forwarding to sign-in.jsp");
    }

    // 로그인 요청 처리
    @PostMapping("/sign-in")
    public String signIn(LoginDTO inputData, RedirectAttributes ra) {
        log.info("/member/sign-in Post! - {}", inputData);

        LoginFlag flag = memberService.login(inputData);

        if (flag == LoginFlag.SUCCESS){
            log.info("login success!!");
            return "redirect:/";
        }
        ra.addFlashAttribute("loginMsg", flag);
        return "redirect:/member/sign-in";
    }
}
