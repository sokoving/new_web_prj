package com.project.web_prj.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public class FileUtils {

    // 1. 사용자가 파일을 업로드했을 때
    // 새로운 파일명을 생성해서 반환하는 메서드
    // ex) 사용자가 상어.jpg를 올렸으면 이름을 저장하기 전에 중복 없는 이름으로 바꿈

    /**
     *
     * @param file - 클라이언트가 업로드한 파일 정보
     * @param uploadPath - 서버의 업로드 루트 디렉토리 (E:/sl_dev/upload)
     * @return - 업로드가 완료된 새로운 파일의 이름
     */
    public static String uploadFile(MultipartFile file, String uploadPath) {

        // 중복이 없는 파일명으로 변경하기
        // ex) 상어.png -> 3dfsfjkdsfds-djksfaqwerij-dsjkfdkj_상어.png
        String newFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        // UUID: 중복 없는 긴 문자열 생성하는 자바 유틸 클래스
        // toString()으로 문자열 반환할 수 있다

        // 업로드 경로 변경(업로드 날짜에 따라 저장하는 파일이 새로 생김)
        // "E:\\study\\upload" >"E:\\study\\upload\\2022\\08\\01"
        String newUploadPath = getNewUploadPath(uploadPath);

        // 파일 업로드 수행
        File f = new File(uploadPath, newFileName);

        try {
            file.transferTo(f);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newFileName;
    }

    /**
     * 원본 업로드 경로를 받아서 일자별 폴더를 생성한 후 최종 경로를 리턴
     * @param uploadPath - 원본 업로드 경로
     * @return 일자별 폴더가 포함된 새로운 얼로드 경로
     */
    private static String getNewUploadPath(String uploadPath) {
        // 오늘 년, 월, 일 정보 가져오기
        LocalDateTime now = LocalDateTime.now();
        int y = now.getYear();
        int m = now.getMonthValue();
        int d = now.getDayOfMonth();

        // 폴더 생성
        String[] dateInfo = {
                String.valueOf(y)
                , len2(m)
                , len2(d)
        };

        String newUploadPath = uploadPath;

        // File.separator : 운영체제에 맞는 디렉토리 경로구분문자를 생성
        // ex) 리눅스 : /, 윈도우 : \
        for (String date : dateInfo) {
            newUploadPath += File.separator + date;

            // 해당 경로대로 폴더 생성
            File dirName = new File(newUploadPath);
            if(!dirName.exists()) dirName.mkdir();
        }


        return null;
    }

    // 한 자릿수 월, 일 정보를 항상 2자리로 만들어주는 메서드
    // ex) 1 -> 01
    private static String len2(int n){
        return new DecimalFormat("00").format(n);
    }
}