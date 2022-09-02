# AWS 웹서버 구축하기
## 1강 AWS EC2 생성하기
### 외부에서 본인이 만든 서비스에 접근하려면 24시간 중단없이 작동하는 서버가 필요합니다.
1. 집에 있는 컴퓨터를 24시간 구동시키는 방법
2. 호스팅 서비스(cafe24 등)를 이용하는 방법
3. 클라우드 서비스(AWS 등)를 이용하는 방법
- 일반적으로는 호스팅 서비스나 집에 있는 컴퓨터를 이용하는 것이 저렴하지만
 + 특정 시간대에 트래픽이 몰리는 경우 유동적으로 서버의 사양을
 + 늘리거나 줄일 수 있는 클라우드 서비스가 유리합니다

### AWS 회원 가입하기
- AWS는 첫 가입시 1년간 무료로 사용할 수 있는 프리티어 서비스를 지원합니다
- 링크: https://aws.amazon.com/ko/free
- *주의!*
 + *1년이 지나면 유료가 되고 사용량에 따라 요금이 부과되는 것을 잊지 말기*
1. 이메일, 계정명, 비밀번호 입력
2. 이 계정에 대해 누구에게 문의해야 하나요?
 - 본인 여권 영문 이름, 영문 주소(네이버 검색)
3. 결제 정보
 - 신용카드 정보를 입력하면 1달러가 결제된다(다시 환불됨)
4. 자격 증명 확인
 - 보안문자 및 코드번호 입력

### EC2 서비스
1. 루트 사용자로 로그인하기
2. 최상단 검색창에서 EC2 검색
 - EC2 클라우드의 가상 서버 클릭
 - 인스턴스 시작
3. 인스턴스 설정
 - 이름: 프로젝트명이든 한글이든 노상관
 - 애플리케이션 및 OS 이미지 : Amazon Linux 추천
 - Amazon Machine Image(AMI) : 프리티어 사용 가능한 것만 무료
   + Amazon Linux 2 AMI (HVM) - Kemel 5.10, SSD Volume Type (선택)
 - 인스턴스 유형 : 프리티어 사용 가능한 것만 무료, T시리즈는 범용
   + t2.micro (선택)
 4. 키페어 생성 *파일이 다운로드 되는데 이걸 분실하거나 깃에 올리거나 하면 안 됨*
  - 키 페어 이름 : 식별 가능한 이름 기입
  - 키 페어 유형 : RSA
  - 프라이빗 키 파일 형식 : .ppk
5. 네트워크 설정
 - 보안 그룹 규칙1
   + 소스 유형
     + 어디서든 설정하면 키만 있으면 어디서는 서버 접속 가능(위험)
     + 내 IP가 안전
 - 보안 그룹 규칙2
   + 유형 : HTTP
   + 프로토콜 :TCP
   + 포트범위 : 80
   + 소스 유형 : 사용자 지정
   + 원본 정보 : 0.0.0.0/0  ::/0
 - 보안 그룹 규칙 3
  + 유형 : HTTPS
  + 프로토콜 :TCP
  + 포트범위 : 443
  + 소스 유형 : 사용자 지정
  + 원본 정보 : 0.0.0.0/0  ::/0
- 보안 그룹 규칙 추가
6. 스토리지 구성
- 1x : 30Gib gp2 루트 볼륨
- 스토리지는 하드디스크 역할, 프리티어 최대치는 30기가바이트
7. 인스턴스 시작 클릭
- 좌측 탭 인스턴스 클릭하면 생성되는 거 볼 수 있음
- 생성이 완료되면 서버의 도메인과 아이피가 할당됩니다
8. 탄력적 IP 생성
- EC2 인스턴스의 경우 서버를 중지하고 재실행 하면 새 IP가 할당되는데
  + 이렇게 되면 접속할 때마다  IP주소를 확인해야 하는 문제가 생깁니다.
  + 그렇기 때문에 고정 IP를 가지게 해야 하는데 이것을 탄력적 IP라고 부릅니다.
- 죄측 탭 네트워크 및 보안 > 탄력적 IP > 탄력적 IP 주소 할당 클릭
- 탄력적 IP 주소 연결
  + 생성 : 인스턴스 확인하고 연결 클릭
- *주의! 탄력적 아이피는 생성하고 EC2 서버에 연결하지 않으면 비용이 발생합니다.*
  + 따라서 생성한 탄력적 아이피는 반드시 EC2에 연결해야 하며
  + 사용할 인스턴스가 없으면 반드시 삭제해야 비용이 발생하지 않습니다
- 인스턴스 탭에서 퍼블릭 DNS와 IPv4 퍼블릭 IP 주소 확인

## 2강 AWS EC2 접속하기
1. 윈도우에서 AWS EC2에 접속하기 위해 putty라는 클라이언트를 설치해야 합니다 
- 링크: https://www.putty.org
  + Download PuTTY
2. Putty 클라이언트 > Host Name에 ec2-user@탄력적IP주소 입력
 - ec2-user@43,200.57.252
3. 왼쪽 탭 Connection > SSH > Auth 클릭
 - Private key file for autherticatoin
  + 아까 받은 ppk파일 선택
4. 왼쪽 탭 Window > Apperance 폰트 크키 조절 가능
5. 왼쪽 탭 Session
 - Saved Sessions > Save (키 설정한 것 기억하기)
 - Open 클릭
   + PuTTY Security Alert > Accept
6. cmd 열림
- [ec2-user@ip-172-31-35-93 ~] $ 라고 뜸
- jdk11 설치 : $ sudo yum install java-11-amazon-corretto.x86_64
- 설치 확인 : $ java -version
  + openjdk version "11.0.16.1" 2022-08-12 LTS
- 타임존 변경 : EC2 서버의 기본 타임존은 UTC > KST로 변경
  + $ sudo rm /etc/localtime
  + $ sudo ln –s /usr/share/zoneinfo/Asia/Seoul /etc/localtime
  + $ date
  + 안되면? $ sudo timedatectl set-timezone 'Asia/Seoul'

## 3강 AWS RDS 생성하기
- AWS RDS란 데이터베이스 서버환경을 지원하는 서비스입니다.
- 우리는 RDS를 통해 복잡한 데이터베이스 구축을 손쉽게 구성하여 사용할 수 있습니다
1. AWS 홈에서 rds 검색
 - RDS > 데이터베이스 생성 클릭
2. 데이터베이스 생성
 - 생성방식 : 표준 생성
 - 엔진 옵션 : MariaDB
 - 템플릿 : 프리티어
 - DB 인스턴스 식별자 : DB 이름 정해서 쓰기
 - *마스터 사용자 이름* : 루트 계정명 = DB 계정 관리자명(잊어먹으면 안 됨)
 - 마스터 암호, 암호 확인 : 비밀번호 설정(어렵게)
 - 스토리지
  + 유형 : 범용 SSD, 할당된 스토리지 : 20, 스토리지 자동 조정 활성화 체크
  + 퍼블릿 액세스 : 예(EC2 서버 포함 로컬 PC에서도 DB 접속 가능해짐)
 - 데이터베이스 생성
3. RDS > 데이터베이스 > 생성중이라고 뜸 
- 상태 : 사용 가능 뜰 때가지 기다림
4. 파라미터 그룹 > 파라미터 그룹 생성

5. 오...

## 4강 AWS 프로젝트 배포하기
1. 톰캣 설치
 - 
2. 톰캣 포트 설정
3. 퍼블릭 DNS(IPv4) 주소로 들어가서 톰캣 로고가 뜨는지 보기(정상 실행)
4. web_prj DataSource 설정 변경
```java
package com.project.web_prj.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

// DB 관련 설정 클래스
@Configuration
@ComponentScan(basePackages = "com.project.web_prj")
@PropertySource("classpath:db_info.properties")
@Getter
public class DataBaseConfig {

    @Value("${aws.rds_user_name}")
    private String username;

    @Value("${aws.rds_password}")
    private String password;

    @Value("${aws.rds_url}")
    private String url;


    // DB접속 정보 설정 (DataSource설정)
    @Bean
    public DataSource dataSource() {

        HikariConfig config = new HikariConfig();
        //=====  ORACLE =====//
//        config.setUsername("spring4");
//        config.setPassword("1234");
//        config.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:xe");
//        config.setDriverClassName("oracle.jdbc.driver.OracleDriver");

        //===== MariaDB =====//
        config.setUsername(username);
        config.setPassword(password);
        config.setJdbcUrl(url);
        config.setDriverClassName("org.mariadb.jdbc.Driver");

        return new HikariDataSource(config);
    }
}
```
5. 보안을 위해서 중요 정보는 유틸에 넣어놓고 유틸값으로 바꾸기
- *보안 정보가 있는 페이지는 깃 이그노어에 등록해서 올리지 않기*
```java
package com.project.web_prj.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DataBaseConfigTest {

    @Autowired DataBaseConfig config;

    @Test
    void propTest() {

        System.out.println(config.getUsername());
        System.out.println(config.getUrl());
    }

}
```
6. UploadController 파일 업로드 설정 변경 후 폴더 생성
```java
public class UploadController{
    // 업로드 파일 저장 경로
    private static final String UPLOAD_PATH = "/home/ec2-user/sl_dev/upload";
}
```
7. build.gradle 설정 변경
- 스트링부트 내장 톰캣 충돌 방지
```
plugins {
    ...
}

bootWar.enabled = false //bootWar와 충돌 방지
war.enabled = true

group = 'com.project'
...
```
- Gradle 빌드 실행 시 테스트를 스킵하는 설정
 + web_prj는 테스트가 불완전하기 때문에 설정하는 것
```
tasks.named('test'){
// useJUnitlatform()
 exclude '**/*'
 }
```
8. 깃허브에 push
*보안 정보가 올라가지 않게 주의*
9. putty - 깃헙에 올린 프로젝트 클론하기
10. 프로젝트로 이동해서 프로젝트 빌드하기
- 빌드 프로그램 실행 권한 부여해 줘야 함
- 빌드가 완료되면 프로젝트 내부의 build.libs 폴더에 War 파일이 생성됨
