--//======== Maria DB ========//--
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

--//======== ORACLE ========//--
CREATE SEQUENCE seq_tbl_board;

DROP TABLE tbl_board;
CREATE TABLE tbl_board (
    board_no NUMBER(10),
    writer VARCHAR2(20) NOT NULL,
    title VARCHAR2(200) NOT NULL,
    content CLOB,
    view_cnt NUMBER(10) DEFAULT 0,
    reg_date DATE DEFAULT SYSDATE,
    CONSTRAINT pk_tbl_board PRIMARY KEY (board_no)
);