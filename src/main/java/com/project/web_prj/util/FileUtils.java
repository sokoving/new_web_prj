package com.project.web_prj.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

public class FileUtils {

    // 1. 사용자가 파일을 업로드했을 때
    // 새로운 파일명을 생성해서 반환하는 메서드
    // ex) 사용자가 상어.jpg를 올렸으면 이름을 저장하기 전에 중복 없는 이름으로 바꿈

    /**
     *
     * @param file - 클라이언트가 업로드한 파일 정보
     * @param uploadPath - 서버의 업로드 루트 디렉토리 (E:\study\upload)
     * @return - 업로드가 완료된  새로운 파일 이름
     */
    public String uploadFile(MultipartFile file, Strnig uploadPath){
        // 중복이 없는 파일명으로 변경하기
        // ex) 상어.png > 3sdlkafjddkfdj-dfkjsldfkj-dkfjdlkf_상어.png
        String newFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename()
        // UUID: 중복 없는 긴 문자열 생성하는 자바 유틸 클래스
            // toString()으로 문자열 반환할 수 있다

        // 파일 업로드 수행
        File f = new File(uploadPath, newFileName);

        try
        return null;
    }

}
