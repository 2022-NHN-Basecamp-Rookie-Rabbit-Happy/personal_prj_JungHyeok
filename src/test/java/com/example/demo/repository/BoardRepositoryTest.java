package com.example.demo.repository;

import com.example.demo.entity.Board;
import com.example.demo.entity.Member;
import com.example.demo.entity.Reply;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest //이 어노테이션 안에 @Transactional 어노테이션이 존재한다.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql({"/dummy-member.sql","/dummy-board.sql","/dummy-reply.sql"})
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    void testBoardRead1(){

        Optional<Board> byId = boardRepository.findById(100L);

        Assertions.assertTrue(byId.isPresent());
        Board board = byId.get();
        Assertions.assertEquals(100, board.getBno());
        Assertions.assertNotNull(board.getWriter());
        Assertions.assertEquals("user100@aaa.com", board.getWriter().getEmail());

    }

    @Test
    @DisplayName("joinQuery 테스트")
    void testReadWithWriter(){
        Object boardWithWriter = boardRepository.getBoardWithWriter(100L);
        Assertions.assertNotNull(boardWithWriter);

        Object[] arr = (Object[]) boardWithWriter;
        Board board = (Board) arr[0];
        Member member = (Member) arr[1];

        Assertions.assertEquals(100L,board.getBno());
        Assertions.assertEquals("USER100",member.getName());
    }

    @Test
    @DisplayName("joinQuery 테스트 ")
    void testGetBoardWithReply(){
        List<Object[]> boardWithReply = boardRepository.getBoardWithReply(100L);

        boardWithReply.forEach(objects -> {
            Board board = (Board) objects[0];
            Reply reply = (Reply) objects[1];
            Assertions.assertEquals(100L,board.getBno());
            Assertions.assertEquals(board.getBno(),reply.getBoard().getBno());
        });
    }

    @Test
    @DisplayName("joinQuery 테스트")
    void testWithReplyCount(){
        PageRequest pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Object[]> boardWithReplyCount = boardRepository.getBoardWithReplyCount(pageable);

        Assertions.assertNotNull(boardWithReplyCount);
    }

    @Test
    @DisplayName("joinQUery 테스트 - 게시물, 글쓴이, 댓글의 갯수를 게시글의 pk로 조회한다.")
    void testRead3(){
        Object result = boardRepository.getBoardByBno(100L);
        Assertions.assertNotNull(result);
        Object[] arr = (Object[]) result;

        Board board = (Board) arr[0];
        Member writer = (Member) arr[1];
        long replyCount = (long) arr[2];

        Assertions.assertEquals(100L,board.getBno());
        Assertions.assertEquals("user100@aaa.com",writer.getEmail());
        System.out.println(replyCount);
        Assertions.assertTrue(replyCount>=0);
    }
}

