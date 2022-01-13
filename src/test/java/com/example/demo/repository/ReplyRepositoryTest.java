package com.example.demo.repository;

import com.example.demo.entity.Board;
import com.example.demo.entity.Reply;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql({"/dummy-member.sql","/dummy-board.sql","/dummy-reply.sql"})
class ReplyRepositoryTest {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    void readReply1(){
        Optional<Reply> byId = replyRepository.findById(1L);
        Assertions.assertTrue(byId.isPresent());

        Reply reply = byId.get();
        Assertions.assertEquals(1L,reply.getRno());
    }

    @Test
    @DisplayName("board 객체로 게시물의 댓글을 조회한다.")
    void testListByBoard(){
        Board board = new Board();
        board.setBno(97L);

        List<Reply> replyList = replyRepository.getRepliesByBoardOrderByRno(board);

        replyList.forEach(reply -> Assertions.assertEquals(board.getBno(),reply.getBoard().getBno()));
    }
}