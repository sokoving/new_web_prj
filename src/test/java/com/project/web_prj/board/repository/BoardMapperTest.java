package com.project.web_prj.board.repository;

import com.project.web_prj.board.dto.ValidateMemberDTO;
import com.project.web_prj.board.domain.Board;
import com.project.web_prj.common.search.Search;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardMapperTest {

    @Autowired
    BoardMapper mapper;

    @Test
    @DisplayName("제목으로 검색된 목록을 조회해야 한다.")
    void searchByTitleTest() {

        Search search = new Search(
                            "tc"
                            , "ㅋㅋ"
                        );

        mapper.findAll2(search).forEach(System.out::println);
    }

    @Test
    @DisplayName("300개의 게시물을 삽입해야 한다.")
    void bulkInsert() {

        Board board;
        for (int i = 1; i <= 300; i++) {
            board = new Board();
            board.setAccount("abcde");
            board.setTitle("제목" + i);
            board.setWriter("길동이" + i);
            board.setContent("안녕하세요요요요요요요~~" + i);
            mapper.save(board);
        }
    }

    @Test
    @DisplayName("특정 게시물에 첨부된 파일 경로들을 조회한다.")
    void findFileNamesTest(){
        // given
        Long bno = 323L;

        // when
        List<String> fileNames = mapper.findFileNames(bno);

        // then
        fileNames.forEach(System.out::println);
        assertEquals(4, fileNames.size());
    }

    @Test
    @DisplayName("게시글 번호로 글쓴이의 account와 auth를 조회할 수 있따")
    void findMemberByBoardNo(){
        Long boardNo = 1L;
        ValidateMemberDTO memberDTO = mapper.findMemberByBoardNo(boardNo);

        String account = memberDTO.getAccount();
        String auth = memberDTO.getAuth().toString();

        System.out.println("account = " + account);
        System.out.println("auth = " + auth);

    }

}