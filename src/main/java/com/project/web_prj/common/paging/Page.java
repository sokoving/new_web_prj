package com.project.web_prj.common.paging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString @Getter
@AllArgsConstructor
// 페이지 정보 클래스
public class Page {

    private int pageNum; // 페이지 번호
    private int amount; // 한 페이지당 배치할 게시물 수

    public Page() {
        this.pageNum = 1;
        this.amount = 10;
    }

    public void setPageNum(int pageNum) {
        if (pageNum <= 0 || pageNum > Integer.MAX_VALUE) {
            this.pageNum = 1;
            return;
        }
        this.pageNum = pageNum;
    }

    public void setAmount(int amount) {
        if (amount < 10 || amount > 100) {
            this.amount = 10;
            return;
        }
        this.amount = amount;
    }

//    매퍼의 #{start}는 getStart() 메서드를 부르는 것
//    MariaDB에서 LIMIT 첫 번재 숫자 구하기
    public int getStart(){
        return (pageNum-1)*amount;
    }
}
