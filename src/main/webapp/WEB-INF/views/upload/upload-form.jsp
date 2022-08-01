<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>

    <style>

        .fileDrop {
            width: 800px;
            height: 400px;
            border: 1px dashed gray;
            display: flex;
            justify-content: center;
            align-items: center;
            font-size: 1.5em;
        }
        .uploaded-list {
            display: flex;
        }
        .img-sizing {
            display: block;
            width: 100px;
            height: 100px;
        }
    </style>

</head>

<body>

    <!-- 파일 업로드를 위한 form - 동기 처리-->
        <!-- 폼이 제출하고 있는 형식 명시 : enctype="multipart/form-data" -->
    <form action="/upload" method="post" enctype="multipart/form-data">
        <!-- multiple 속성 : 복수 파일 업로드 -->
        <input type="file" name="file" multiple>
        <button type="submit">업로드</button>

    </form>

    <!-- 비동기 통신을 통한 실시간 파일 업로드 처리-->
    <div class="fileDrop">
        <span>DROP HERE!!</span>
    </div>

    <script>

        // start jQuery(jQuery 즉시실행 함수, jQuery 구문 시작)
        $(document).ready(function () {

            // drag & drop 이벤트
            const $dropBox = $('.fileDrop');

            // drag 진입 이벤트 (on = addEventListener)
            $dropBox.on('dragover dragenter', e => {
                e.preventDefault(); // 이미지 파일 드랍했을 때 열리는 거 막기
                $dropBox
                    .css('border-color', 'red')
                    .css('background', 'lightgray');
            });

            // drag 탈출 이벤트 (on = addEventListener)
            $dropBox.on('dragleave', e => {
                e.preventDefault(); // 이미지 파일 드랍했을 때 열리는 거 막기
                $dropBox
                    .css('border-color', 'red')
                    .css('background', 'lightgray');
            });

            

        });
        // end jQuery


    </script>



</body>

</html>