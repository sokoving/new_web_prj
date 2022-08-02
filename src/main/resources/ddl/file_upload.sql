-- 첨부파일 정보를 가지는 테이블 생성
CREATE TABLE file_upload (
    file_name VARCHAR2(150),    -- 파일 식별 경로 ex) /2020/08/01/dlafkd_상어.jpg > 중복 없다
-- 추가하면 좋을 메타 데이터
--    origin_name  -- 파일 원래 이름 ex) 상어.jpg
--    file_size     -- 파일 크키
--    exension      -- 파일 확장자
--    expire_date   -- 파일 파기 날
    reg_date DATE DEFAULT SYSDATE,
    bno NUMBER(10) NOT NULL
);

-- PK, FK 부여
ALTER TABLE file_upload
ADD CONSTRAINT pk_file_name
PRIMARY KEY (file_name);

ALTER TABLE file_upload     -- fk는 1:M 관계에서 M 쪽에 건다
ADD CONSTRAINT fk_file_upload
FOREIGN KEY (bno)
REFERENCES tbl_board (board_no)
ON DELETE CASCADE;      -- 게시물 지워지면 첨부파일 같이 삭제 옵션