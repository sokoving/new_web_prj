truncate table tbl_reply;
truncate table FILE_UPLOAD;
truncate table tbl_board;

delete from tbl_board;
commit;

ALTER TABLE tbl_board ADD account VARCHAR2(50) NOT NULL;
ALTER TABLE tbl_reply ADD account VARCHAR2(50) NOT NULL;

ALTER TABLE tbl_member ADD session_id VARCHAR2(200) DEFAULT 'none';
ALTER TABLE tbl_member ADD limit_time DATE;