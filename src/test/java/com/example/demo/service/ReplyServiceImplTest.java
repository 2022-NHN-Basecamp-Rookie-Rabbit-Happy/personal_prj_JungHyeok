package com.example.demo.service;

import com.example.demo.dto.ReplyDTO;
import com.example.demo.mapper.ReplyMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@Sql({"/dummy-member.sql","/dummy-board.sql","/dummy-reply.sql"})
class ReplyServiceImplTest {

    @Autowired
    private ReplyService replyService;

    @Test
    @DisplayName("게시글의 번호로 댓글을 조회한다.")
    void testGetList(){
        Long bno = 100L;
        List<ReplyDTO> list = replyService.getList(bno);
        list.forEach(replyDTO -> Assertions.assertEquals(replyDTO.getBno(),bno));
    }
}