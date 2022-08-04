package com.project.web_prj.common.api;

import com.project.web_prj.board.domain.Board;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// jsp 뷰포워딩을 하지않고 클라이언트에게 JSON데이터를 전송함
@RestController
@Log4j2
public class RestBasicController {

    @GetMapping("/api/hello")
    public String hello() {
        return "hello!!!";
    }
    @GetMapping("/api/board")
    public Board board() {
        Board board = new Board();
        board.setBoardNo(10L);
        board.setContent("할룽~");
        board.setTitle("메룽~~");
        board.setWriter("박영희");


        return board;
    }

    @GetMapping("/api/arr")
    public String[] arr() {
        String[] foods = {"짜장면", "레몬에이드", "볶음밥"};
        return foods;
    }

    // post 요청처리
    @PostMapping("/api/join")
    public String join(@RequestBody List<String> info) {
        log.info("/api/join POST!! - {}", info);
        return "POST-OK";
    }
    // put 요청처리
    @PutMapping("/api/join")
    public String joinPut(@RequestBody Board board) {
        log.info("/api/join PUT!! - {}", board);
        return "PUT-OK";
    }
    // delete 요청처리
    @DeleteMapping("/api/join")
    public String joinDelete() {
        log.info("/api/join DELETE!!");
        return "DEL-OK";
    }


    // @Controller에서  json으로 보내는 법 > @ResponseBody
    @GetMapping("/api/haha")
    @ResponseBody // 리턴 데이터가 뷰포워딩이 아닌 json으로 전달됨
    public Map<String, String> haha(){
        HashMap<String, String> map = new HashMap<>();
        map.put("a", "aaa");
        map.put("b", "bbb");
        map.put("c", "ccc");
        return map;
    }

    // @RestController에서 뷰포워딩 하는 법 > 리턴 타입을 ModelAndView로 하고 mv에 jsp 경로 적고 리턴
    @GetMapping("/hoho")
    public ModelAndView hoho() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        return mv;
    }

}
