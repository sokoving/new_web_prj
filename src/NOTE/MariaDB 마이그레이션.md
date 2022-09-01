# 준비물 : 멀쩡히 돌아가는 Oracle DB 웹

# 1. Maria DB 서버 설치 및 계정 생성, 테스트
## 다운로드
- 링크 : <https://mariadb.org/download/?t=mariadb&p=mariadb&r=10.7.5&os=windows&cpu=x86_64&pkg=msi&m=blendbyte>
- MariaDB Server : 10.7.5
- Operation System : Window
- Architecture : x86_64
- MSI package : MSI Package
## 세팅하기
- Modify password ofr database user 'root' : 비밀번호(어려운 걸로)
- Enable access from remote machines for 'root' user : 원격으로 디비 접속 여부 체크(선택)
- Use UTF8 as default server's character set : 한글 쓰기 위함(필수)
## 설치 테스트 및 계정 생성
-cmd 실행
- cd "C:\Program Files\MariaDB 10.7\bin"
- mysql -u root -p
- 세팅할 때 설정한 비밀번호 입력
- show databases;
 + information_schema, mysql, performance_schema, sys : 기본 계정(건드리지 말 것)
- create database spring4; (새 계정 생성)
 + Query OK, 1 row affected
- show databases;
 + spring4 계정 생성됨
- use spring4;
 + database changed (spring4 계정 사용)
- 테스트 sql
 + select current_timestamp from dual;
 + select current_timestamp from dual; (현재 시간)


# 2. MariaDB 클라이언트 설치하기
## 다운로드
- 링크 : <https://dev.mysql.com/downloads/windows/installer/8.0.html>
- 버전
 + 5.5M 버전 : 인터넷 연결해야 쓸 수 있음
 + 448.3M 버전 : 인터넷 연결 없이 사용 가능
- Download 클릭
 + 로그인하라고 하는데 아래에 'No thanks, just start my download' 누르면 다운 시작
## MySQL Installer
- Choosing a Setup Type : Custom
- Select Produts
 + Application > MySQL Workbench 8.0 > MySQL Workbench 8.0.30 -X64
 + -> 화살표 클릭, Next
- 필요한 파일 있은 Excute 눌러서 설치하고 Next

# 3. MySQL Workbench
## 새로 연결하기
- Home > MySQL Connection (+) 버튼 클릭
 + Connection Name: local_admin (마음대로?)
 + Connection Method: Standard (TCP/IP)
 + Hostaname: 127.0.0.1
 + Port: 3306 (MariaDB 포트, MySQL과 동일하니 깔려 있다면 포트 변경할 것)
 + Username: root
 + password : Store In Vault 클릭, 비밀번호 입력, OK
 + Default Schema : spring4
- Test Connection 통과했다면 OK
 + Successfully made the MySQL connection
## 테스트
- select current_timestamp() from dual;
- show databases;

--------------------------------------------------------
# web-prj
## 1. DataBaseconfig : 설정 바꾸기
```java
package com.project.web_prj.config;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
// DB 관련 설정 클래스
@Configuration
@ComponentScan(basePackages = "com.project.web_prj")
public class DataBaseConfig {

    // DB접속 정보 설정 (DataSource설정)
    @Bean
    public DataSource dataSource() {

        HikariConfig config = new HikariConfig();
        //======== Maria DB ========//
        config.setUsername("root");
        config.setPassword("mariaDB");
        config.setJdbcUrl("jdbc:mariadb://localhost:3306/spring4");
        config.setDriverClassName("org.mariadb.jdbc.Driver");

        return new HikariDataSource(config);
    }
}
```

## 2. build.gradle : 라이브러리 추가 
```
// https://mvnrepository.com/artifact/org.mariadb.jdbc/mariadb-java-client
	implementation 'org.mariadb.jdbc:mariadb-java-client:3.0.6'
```

## 3. WebPrjApplication : 서버 실행
- 잘 연동이 됐다면 서버 연결됨
- 튕긴다면 비밀번호나 오타 없는지 확인할 것
- 테이블이 없기 때문에 페이지 접속은 안 된다.

-------------------------------------------------------

# MySQL Workbench 테이블 만들기
```
CREATE TABLE tbl_board (
    board_no INT(10) AUTO_INCREMENT,        -- sequence -> AUTO_INCREMENT 사용 (default처럼 자동 삽입됨으로 mapper에서 안 써도 됨)
    writer VARCHAR(20) NOT NULL,            -- VARCHAR2(20) -> VARCHAR(20)
    title VARCHAR(200) NOT NULL,
    content TEXT,
    view_cnt INT(10) DEFAULT 0,          -- NUMBER(10) -> INT(10)
    reg_date DATETIME DEFAULT current_timestamp,    -- SYSDATE -> current_timestamp
    CONSTRAINT pk_tbl_board PRIMARY KEY (board_no)
);
-- table 만들었을 때 빨간불만 안 들어오면 됨(UTF 설정 때문)
```

# Mapper SQL 수정
``` auto_increment 속성이면 자동으로 들어가기 때문에 값을 안 넣어도 됨
    <insert id="save">
        INSERT INTO tbl_board
        (board_no, writer, title, content, account)
        VALUES (seq_tbl_board.nextval, #{writer}, #{title}, #{content}, #{account})
    </insert>

    <insert id="save">
        INSERT INTO tbl_board
        (writer, title, content, account)
        VALUES (#{writer}, #{title}, #{content}, #{account})
    </insert>

```

``` 매퍼의 #{start}는 getStart() 메서드를 부르는 것
class Page
    public int getStart(){
        return (pageNum-1)*amount;
    }
```
```  원본 findAll2
      <select id="findAll2" resultMap="boardMap">
            SELECT  *
            FROM (
                    SELECT ROWNUM rn, v_board.*
                    FROM (
                            SELECT *
                            FROM tbl_board
                            <if test="type == 'title'">WHERE title LIKE '%' || #{keyword} || '%'</if>
                            <if test="type == 'writer'">WHERE writer LIKE '%' || #{keyword} || '%'</if>
                            <if test="type == 'content'">WHERE content LIKE '%' || #{keyword} || '%'</if>
                            <if test="type == 'tc'">
                                WHERE title LIKE '%' || #{keyword} || '%'
                                    OR content LIKE '%' || #{keyword} || '%'
                            </if>
                            ORDER BY board_no DESC
                            ) v_board
                  )
            WHERE rn BETWEEN (#{pageNum} - 1) * #{amount} + 1 AND (#{pageNum} * #{amount})
        </select>
```
```검색조건문 분리
    <sql id="search">
        <if test="type == 'title'">WHERE title LIKE CONCAT('%', #{keyword}, '%')</if>
        <if test="type == 'writer'">WHERE writer LIKE CONCAT('%', #{keyword}, '%')</if>
        <if test="type == 'content'">WHERE content LIKE CONCAT('%', #{keyword}, '%')</if>
        <if test="type == 'tc'">
            WHERE title LIKE CONCAT('%', #{keyword}, '%')
            OR content LIKE CONCAT('%', #{keyword}, '%')
        </if>
    </sql>
```
``` maria DB findAll2 sql
    <select id="findAll2" resultMap="boardMap">
        SELECT *
        FROM tbl_board
        <include refid="search" />
        ORDER BY board_no DESC
        LIMIT #{start}, #{amount}
    </select>
```