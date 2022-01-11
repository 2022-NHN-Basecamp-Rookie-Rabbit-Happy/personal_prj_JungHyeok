package com.example.demo.repository;

import com.example.demo.entity.GuestBook;
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

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@Sql("/dummy.sql")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //DataJpa의 기본 내장 메모리 디비로 바꿔치지 않겠다.
class GuestbookRepositoryTest {

    @Autowired
    GuestbookRepository guestbookRepository;

    @Test
    @DisplayName("dummy.sql 삽입테스트")
    void test(){
        List<GuestBook> all = guestbookRepository.findAll();
        Assertions.assertEquals(300,all.size());
    }
}