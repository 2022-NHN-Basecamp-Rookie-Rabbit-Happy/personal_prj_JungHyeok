package com.example.demo.repository;

import com.example.demo.entity.Reply;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

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
}