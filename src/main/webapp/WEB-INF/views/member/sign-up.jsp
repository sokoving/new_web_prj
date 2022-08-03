<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">

<head>

    <%@ include file="../include/static-head.jsp" %>

    <style>
        .wrap {
            margin: 100px auto;
        }

        .c-red {
            color: rgb(255, 0, 0);
        }

        .c-blue {
            color: rgb(0, 0, 255);
        }
    </style>

</head>

<body>

    <div class="wrap">

        <%@ include file="../include/header.jsp" %>

        <div class="container">
            <div class="row">
                <div class="offset-md-2 col-md-4">
                    <div class="card" style="width:200%;">
                        <div class="card-header text-white" style="background: #343A40;">
                            <h2><span style="color: gray;">MVC</span> 회원 가입</h2>
                        </div>
                        <div class="card-body">


                            <form action="/member/sign-up" name="signup" id="signUpForm" method="post"
                                style="margin-bottom: 0;">

                                <table style="cellpadding: 0; cellspacing: 0; margin: 0 auto; width: 100%">
                                    <tr>
                                        <td style="text-align: left">
                                            <p><strong>아이디를 입력해주세요.</strong>&nbsp;&nbsp;&nbsp;
                                                <span id="idChk"></span></p>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><input type="text" name="account" id="user_id"
                                                class="form-control tooltipstered" maxlength="14" required="required"
                                                aria-required="true"
                                                style="margin-bottom: 25px; width: 100%; height: 40px; border: 1px solid #d9d9de"
                                                placeholder="숫자와 영어로 4-14자">
                                        </td>

                                    </tr>

                                    <tr>
                                        <td style="text-align: left">
                                            <p><strong>비밀번호를 입력해주세요.</strong>&nbsp;&nbsp;&nbsp;<span id="pwChk"></span>
                                            </p>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><input type="password" size="17" maxlength="20" id="password"
                                                name="password" class="form-control tooltipstered" maxlength="20"
                                                required="required" aria-required="true"
                                                style="ime-mode: inactive; margin-bottom: 25px; height: 40px; border: 1px solid #d9d9de"
                                                placeholder="영문과 특수문자를 포함한 최소 8자"></td>
                                    </tr>
                                    <tr>
                                        <td style="text-align: left">
                                            <p><strong>비밀번호를 재확인해주세요.</strong>&nbsp;&nbsp;&nbsp;<span
                                                    id="pwChk2"></span></p>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><input type="password" size="17" maxlength="20" id="password_check"
                                                name="pw_check" class="form-control tooltipstered" maxlength="20"
                                                required="required" aria-required="true"
                                                style="ime-mode: inactive; margin-bottom: 25px; height: 40px; border: 1px solid #d9d9de"
                                                placeholder="비밀번호가 일치해야합니다."></td>
                                    </tr>

                                    <tr>
                                        <td style="text-align: left">
                                            <p><strong>이름을 입력해주세요.</strong>&nbsp;&nbsp;&nbsp;<span id="nameChk"></span>
                                            </p>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><input type="text" name="name" id="user_name"
                                                class="form-control tooltipstered" maxlength="6" required="required"
                                                aria-required="true"
                                                style="margin-bottom: 25px; width: 100%; height: 40px; border: 1px solid #d9d9de"
                                                placeholder="한글로 최대 6자"></td>
                                    </tr>


                                    <tr>
                                        <td style="text-align: left">
                                            <p><strong>이메일을 입력해주세요.</strong>&nbsp;&nbsp;&nbsp;<span
                                                    id="emailChk"></span></p>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><input type="email" name="email" id="user_email"
                                                class="form-control tooltipstered" required="required"
                                                aria-required="true"
                                                style="margin-bottom: 25px; width: 100%; height: 40px; border: 1px solid #d9d9de"
                                                placeholder="ex) abc@mvc.com"></td>
                                    </tr>


                                    <tr>
                                        <td style="padding-top: 10px; text-align: center">
                                            <p><strong>회원가입하셔서 더 많은 서비스를 사용하세요~~!</strong></p>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="width: 100%; text-align: center; colspan: 2;">
                                            <input type="button" value="회원가입" class="btn form-control tooltipstered"
                                                id="signup-btn"
                                                style="background: gray; margin-top: 0; height: 40px; color: white; border: 0px solid #388E3C; opacity: 0.8">
                                        </td>
                                    </tr>

                                </table>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script>
            // 회원가입 폼 검증
            $(document).ready(function () {

                //입력값 검증 정규표현식
                const getIdCheck = RegExp(/^[a-zA-Z0-9]{4,14}$/);
                // ^ 시작 / $ 끝 / a-zA-Z0-9 a부터z, A부터Z, 0부터9 / {4,14} 최소 4자, 최대 14자
                const getPwCheck = RegExp(
                    /([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])/);
                // 특수문자 포함
                const getName = RegExp(/^[가-힣]+$/);
                // 한글로 써라
                const getMail = RegExp(/^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/);
                // 영어로 시작해서 @ 그리고 다시 영어,숫자 그리고 . 그리고 영어, 숫자

                // 입력값 검증 배열
                // 1: 아이디, 2: 비밀번호, 3: 비밀번호 확인, 4: 이름, 5: 이메일
                // 아래 값이 모두 true로 바뀌면 회원가입 폼을 submit
                const checkArr = [false, false, false, false, false]

                // 1. 아이디 검증
                const $idInput = $('#user_id');

                $idInput.on('keyup', e => {

                    // 아이디를 입력하지 않은 경우(빈 문자열인 경우)
                    if ($idInput.val().trim() === '') {
                        $idInput.css('border-color', 'red');
                        $('#idChk').html('<b class="c-red">[아이디는 필수값입니다.]</b>');
                        checkArr[0] = false;
                    }

                    // 아이디를 패턴에 맞지 않게 입력하였을 경우
                    // test() 메서드는 정규표현식을 검증하여 입력값이 표현식과
                    // 일치하면 true, 일치하지 않으면 false를 리턴
                    else if (!getIdCheck.test($idInput.val())) {
                        $idInput.css('border-color', 'red');
                        $('#idChk').html('<b class="c-red">[영문, 숫자로 4~14자 사이로 작성해 주세요.]</b>');
                        checkArr[0] = false;

                    } else {
                        fetch('/member/check?type=account&value=' + $idInput.val())
                            .then(res => {
                                console.log('flag', flag);
                                if (flag === 'true') {
                                    $idInput.css('border-color', 'red');
                                    $('#idChk').html(
                                        '<b class="c-red">[중복된 아이디입니다. ]</b>');
                                    checkArr[0] = false;

                                } else {
                                    // 정상적으로 입력하고 중복이 아닌 경우
                                    $idInput.css('border-color', 'skyblue');
                                    $('#idChk').html('<b class="c-blue">[사용 가능한 아이디입니다.]</b>');
                                    checkArr[0] = true;
                                }
                            })


                    }
                })

            });
        </script>

        <%@ include file="../include/footer.jsp" %>

    </div>

</body>

</html>