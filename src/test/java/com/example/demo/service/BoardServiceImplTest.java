package com.example.demo.service;

import com.example.demo.dto.BoardDTO;
import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResultDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class BoardServiceImplTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("board register test")
    void test1(){
        Member member = new Member("user55@aaa.com", "1111", "user555");
        entityManager.persist(member);

        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setContent("Test...");
        boardDTO.setTitle("Test.");
        boardDTO.setWriterEmail(member.getEmail());

        Long register = boardService.register(boardDTO);
        Assertions.assertNotNull(register);
    }

    @Test
    @DisplayName("getList 테스트")
    @Sql({"/dummy-member.sql","/dummy-board.sql","/dummy-reply.sql"})
    void test2(){
        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<BoardDTO, Object[]> list = boardService.getList(pageRequestDTO);
        Assertions.assertEquals(10,list.getDtoList().size());
    }

    @Test
    @DisplayName("getByBno Test")
    @Sql({"/dummy-member.sql","/dummy-board.sql","/dummy-reply.sql"})
    void test3(){
        BoardDTO boardDTO = boardService.get(100L);
        Assertions.assertEquals(100L,boardDTO.getBno());
        Assertions.assertEquals("Title...100",boardDTO.getTitle());
        Assertions.assertEquals("Content...100",boardDTO.getContent());
        Assertions.assertEquals("user100@aaa.com",boardDTO.getWriterEmail());
        Assertions.assertEquals("USER100",boardDTO.getWriterName());
        System.out.println(boardDTO.getReplyCount());
        Assertions.assertTrue(boardDTO.getReplyCount()>=0);

    }

    @Test
    @DisplayName("delete Test")
    @Sql({"/dummy-member.sql","/dummy-board.sql","/dummy-reply.sql"})
    void test4(){
        boardService.removeWithReplies(1L);

        Board board = entityManager.find(Board.class, 1L);
        Assertions.assertNull(board);
    }

    @Test
    @DisplayName("modify Test")
    @Sql({"/dummy-member.sql","/dummy-board.sql","/dummy-reply.sql"})
    void test5(){
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBno(2L);
        boardDTO.setTitle("제목 변경");
        boardDTO.setContent("내용 변경");

        boardService.modify(boardDTO);

        BoardDTO inquiry = boardService.get(boardDTO.getBno());
        Assertions.assertEquals(boardDTO.getTitle(),inquiry.getTitle());
        Assertions.assertEquals(boardDTO.getContent(),inquiry.getContent());
    }
}