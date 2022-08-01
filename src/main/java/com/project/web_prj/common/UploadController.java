package com.project.web_prj.common;

import com.project.web_prj.util.FileUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Controller
@Log4j2
public class UploadController {

    // 업로드 파일 저장 경로
    private static final String UPLOAD_PATH ="E:\\study\\upload"; // 경로에 \ 두번 써야 하는 건 탈출문자 방지용

    // upload-form.jsp로 포워딩하는 요청
    @GetMapping("/upload-form")
    public String uploadForm() {
        return "upload/upload-form";
    }

    // 파일 업로드 처리를 위한 요청
        // MultipartFile 클라이언트가 전송한 파일 관련 정보들을 담은 객체(스프링)
        // ex) 원본 파일명, 파일 용량, 파일 컨텐츠 타입 등등
        // 매개변수명 file은 jsp 파일의 폼에서 제출한 input의 name="file"과 일치시킨 것
    @PostMapping("/upload")
    // 단수 파일 받기
    // public String upload(MultipartFile file) {
    
    // 복수 파일 받기
    public String upload(@RequestParam("file") List<MultipartFile> fileList) {
        log.info("/upload POST! - {}", fileList);

        for (MultipartFile file : fileList) {
        log.info("file-name: {}", file.getName());
        log.info("file-origin-name: {}", file.getOriginalFilename());
        log.info("file-size: {}KB", (double) file.getSize() / 1024);
        log.info("file-type: {}", file.getContentType());
        System.out.println("==================================================================");


        // 서버에 업로드파일 저장

            // 1. 세이브파일 객체 생성
            // - 첫번째 파라미터는 파일 저장결로 지정, 두번째 파일명 지정
/*
        File f = new File(uploadPath, file.getOriginalFilename());

        try {
            file.transferTo(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
        FileUtils.uploadFile(file, UPLOAD_PATH);
        }
        return "redirect:/upload-form";
    }

    // 비동기 요청 파일 업로드 처리
    @PostMapping("/ajax-upload")
    @ResponseBody
    public void ajaxUpload(List<MultipartFile> files){
        log.info("/ajax-upload POST!! - {}", files.get(0).getOriginalFilename());

        // 클라이언트가 전송한 파일 업로드하기
        for (MultipartFile file : files) {
            FileUtils.uploadFile(file, UPLOAD_PATH);
        }
    }

}
