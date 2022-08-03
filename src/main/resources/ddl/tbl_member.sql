-- 회원 관리 테이블
CREATE TABLE tbl_member (
    account VARCHAR2(50),   -- 계정명
    password VARCHAR2(150) NOT NULL,
        -- 비밀번호 : DB에 암호화해서 저장해야 하기 때문에 바이트가 큼(평문으로 저장하면 안 됨)
        -- 사이트 관리자가 회원의 비밀번호 원본을 볼 수 없어야 함
    name VARCHAR2(50) NOT NULL,     -- 이름
    email VARCHAR2(100) NOT NULL UNIQUE,
        -- 최근 추세는 이메일과 어카운트를 통일하는 것
        -- JMS를 쓰면 이메일 인증할 수 있음
    auth VARCHAR2(20) DEFAULT 'COMMON',
        -- 권한: common  / admin
    reg_date DATE DEFAULT SYSDATE,
    -- 추가로 필요한 컬럼
        -- 로그인 이력 테이블 (필수사항)
        -- 마지막 로그인 시간(?)
    CONSTRAINT pk_member PRIMARY KEY (account)
);



SELECT * FROM tbl_member;
