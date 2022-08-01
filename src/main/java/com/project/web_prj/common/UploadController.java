package com.project.web_prj.common;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Controller
@Log4j2
public class UploadController {

    // upload-form.jsp로 포워딩하는 요청
    @GetMapping("/upload-form")
    public String uploadForm(){
        return "upload/upload-form";
    }

    // 파일 업로드 처리를 위한 요청
        // MultipartFile 클라이언트가 전송한 파일 관련 정보들을 담은 객체(스프링)
        // ex) 원본 파일명, 파일 용량, 파일 컨텐츠 타입 등등
        // 매개변수명 file은 jsp 파일의 폼에서 제출한 input의 name="file"과 일치시킨 것
    @PostMapping("/upload")
    public String upload(MultipartFile file){
        log.info("/upload POST! = {}", file);

        // 파일 정보 확인
        log.info("file-name: {}", file.getName());
        log.info("file-OriginalFilename: {}", file.getOriginalFilename());
        log.info("file-Size: {}", file.getSize());
        log.info("file-ContentType: {}", file.getContentType());

        // 서버에 업로드파일 저장
            // 업로드 파일 저장 경로
        String uploadPath="E:\\study\\upload"; // 경로에 \ 두번 써야 하는 건 탈출문자 방지용
            // 1. 세이브파일 객체 생성
            // - 첫번째 파라미터는 파일 저장결로 지정, 두번째 파일명 지정
        File f = new File(uploadPath, file.getOriginalFilename());

        return "redirect:/upload-form";
    }
}
