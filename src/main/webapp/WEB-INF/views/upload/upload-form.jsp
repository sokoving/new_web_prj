<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>

    <!-- jquery -->
    <script src="/js/jquery-3.3.1.min.js"></script>

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

    <!-- 비동기 통신을 통한 실시간 파일 업로드 처리 -->
    <div class="fileDrop">
        <span>DROP HERE!!</span>
    </div>

    <!-- 
        - 파일 정보를 서버로 보내기 위해서는 <input type="file"> 이 필요
        - 해당 input태그는 사용자에게 보여주어 파일을 직접 선택하게 할 것이냐
          혹은 드래그앤 드롭으로만 처리를 할 것이냐에 따라 display를 상태를 결정
     -->
     <!-- 동기 통신과 다른 점은 form이 없다는 것, 파일 정보를 담을 input창은 필요하지만 사용자 눈에 보일 필요는 없다 -->
    <div class="uploadDiv">
        <input type="file" name="files" id="ajax-file" style="display:none;">
    </div>

    <!-- 업로드된 이미지의 썸네일을 보여주는 영역 -->
    <div class="uploaded-list">

    </div>


    <script>
        // start jQuery(jQuery 즉시실행 함수, jQuery 구문 시작)
        $(document).ready(function () {
            function isImageFile(originFileName) {
                //정규표현식
                const pattern = /jpg$|gif$|png$/i;
                return originFileName.match(pattern);
            }
            // 파일의 확장자에 따른 렌더링 처리
            function checkExtType(fileName) {
                //원본 파일 명 추출
                let originFileName = fileName.substring(fileName.indexOf("_") + 1);
                //확장자 추출후 이미지인지까지 확인
                if (isImageFile(originFileName)) { // 파일이 이미지라면
                    const $img = document.createElement('img');
                    $img.classList.add('img-sizing');
                    $img.setAttribute('src', '/loadFile?fileName=' + fileName);
                    $img.setAttribute('alt', originFileName);
                    $('.uploaded-list').append($img);
                }
            }
            // 드롭한 파일을 화면에 보여주는 함수
            function showFileData(fileNames) {
                // 이미지인지? 이미지가 아닌지에 따라 구분하여 처리
                // 이미지면 썸네일을 렌더링하고 아니면 다운로드 링크를 렌더링한다.
                for (let fileName of fileNames) {
                    checkExtType(fileName);
                }
            }
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
                    .css('border-color', 'gray')
                    .css('background', 'transparent');
            });

            // drop 이벤트
            $dropBox.on('drop', e => {
                e.preventDefault();
                // console.log('드롭 이벤트 작동!');
                // 드롭된 파일 정보를 서버로 전송
                // 1. 드롭된 파일 데이터 읽기
                console.log(e);

                // e에서 파일 정보가 있는 곳
                const files = e.originalEvent.dataTransfer.files;
                // console.log('drop file data: ', files);
                // 2. 읽은 파일 데이터를 input[type=file]태그에 저장
                const $fileInput = $('#ajax-file');
                $fileInput.prop('files', files); // 첫번째 파라미터는 input의 name 속성과 맞추기

                // console.log($fileInput);
                // 3. 파일 데이터를 비동기 전송하기 위해서는 FormData객체가 필요
                const formData = new FormData();
                // 4. 전송할 파일들을 전부 FormData안에 포장
                for (let file of $fileInput[0].files) {
                    formData.append('files', file);
                }

                // 5. 비동기 요청 전송
                const reqInfo = {
                    method: 'POST',
                    body: formData
                };
                fetch('/ajax-upload', reqInfo)
                    .then(res => {
                        //console.log(res.status);
                        return res.json();
                    })
                    .then(fileNames => {
                        console.log(fileNames);
                        showFileData(fileNames);
                    });
            });
        });
        // end jQuery
    </script>


</body>

</html>