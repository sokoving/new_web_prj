# 주의사항
1. CREATE TABLE은 오라클에서 직접 입력해 생성
2. 프로젝트를 진행할 때 mapper를 만들고 테스트하는 것이 우선순위
3. mapper 만들고 테스트는 필수!

- SQL문에 오타나 괄호 오류가 나면 예외 발생
 + 들여쓰기를 깔끔하게 해서 보기 좋게 쓰자

- 테이블을 조인해서 SELECT 할 때는
 + 조회할 컬럼들과 매칭되는 필드를 가진 개별 클래스와 resultMap을 만들어 사용한다
 + sql문 쓸 때 별칭 잊지 말고 쓰기



# 1. xml !DOCTYPE
```  namespace 속성 : 사용할 인터페이스의 풀 패키지경로 + 인터페이스이름
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.project.web_prj.board.repository.BoardMapper">

        <!-- mapper 태그 안에 게시글 등록 SQL 작성한다  -->

</mapper>
```

# 2. select 태그
 + id 속성 : 추상메서드 이름
 + resultType 속성
``` 단일 컬럼 조회: resultType 속성, 값으로 데이터 타입 명시
            <select id="findAll" resultType="int">
                SELECT price FROM tbl_product
                ORDER BY price DESC
            </select>
```
``` 컬럼과 필드명이 완전히 같다면
    resultType="com.project.web_prj.board.dto.ValidateMemberDTO"
```
 + resultMap 속성
``` 다중 컬럼 조회: resultMap 속성, 값으로 사용할 resultMap의 id 명시
            <select id="findOne" resultMap="boardMap">
                SELECT *
                FROM tbl_board
                WHERE board_no=#{boardNo}
            </select>
```
 + reultMap 설정
  - DB 컬럼과 필드명이 완벽히 일치하면 설정하지 않아도 되지만
    + 자바에 스네이크 케이스를 쓰면 스프링에서 처리하다 오류가 날 수 있고
    + DB에 캐멀 케이스를 쓰면 대소문자 구분이 되지 않기 때문에
    + ResultMap 설정을 해야 한다
  - 모든 셀렉트에서 이 설정을 사용할 수 있다
  - id 속성 : 해당 맵을 쓸 select 태그의 resultMap 값과 일치시킨다
  - property 속성 : 자바 필드명, column 속성 : DB 컬럼명
``` DB 컬럼과 자바 클래스 필드명의 차이를 지정
          <resultMap id="boardMap" type="com.project.web_prj.board.domain.Board">
              <result property="boardNo" column="board_no"/>
              <result property="viewCnt" column="view_cnt"/>
              <result property="regDate" column="reg_date"/>
          </resultMap>
```

# 3. delete, update 태그
``` 
    <delete id="remove">
        DELETE FROM tbl_board
        WHERE board_no=#{boardNo}
    </delete>

    <update id="modify">
        UPDATE tbl_board
        SET writer = #{writer}, title=#{title}, content=#{content}
        WHERE board_no=#{boardNo}
    </update>
```

# 4. 동적 sql
``` 쿼리 파라미터에 따라 다른 sql문을 보낸다
    <select id="getTotalCount2" resultType="int">
        SELECT COUNT(*)
        FROM tbl_board
            <if test="type == 'title'">
                WHERE title LIKE '%' || #{keyword} || '%'
            </if>
            <if test="type == 'writer'">
                WHERE writer LIKE '%' || #{keyword} || '%'
            </if>
            <if test="type == 'content'">
                WHERE content LIKE '%' || #{keyword} || '%'
            </if>
            <if test="type == 'tc'">
                WHERE title LIKE '%' || #{keyword} || '%'
                    OR content LIKE '%' || #{keyword} || '%'
            </if>
    </select>
```