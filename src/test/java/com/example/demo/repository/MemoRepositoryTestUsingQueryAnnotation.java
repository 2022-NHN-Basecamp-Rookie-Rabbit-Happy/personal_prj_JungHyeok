package com.example.demo.repository;

import com.example.demo.entity.Memo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
class MemoRepositoryTestUsingQueryAnnotation {

    @Autowired
    MemoRepository memoRepository;

    @Autowired
    TestEntityManager testEntityManager;

    private Memo memo;

    @BeforeEach
    void setUp(){
        memo = Memo.builder().memoText("testText").build();
        memoRepository.save(memo);
    }

    @Test
    @DisplayName("쿼리어노테이션 테스트 - updateMemoText")
    void test1(){
        final String updateText = "updateText";
        memoRepository.updateMemoText(memo.getMno(), updateText);
        testEntityManager.clear();

        Optional<Memo> byId = memoRepository.findById(memo.getMno());
        Assertions.assertTrue(byId.isPresent());

        Memo inquiryMemo = byId.get();
        Assertions.assertEquals(updateText,inquiryMemo.getMemoText());
    }

    @Test
    @DisplayName("쿼리 어노테이션 테스트 - updateMemoText")
    void test2(){
        final String updateText = "updateText!!!";
        memoRepository.updateMemoText(Memo.builder().mno(memo.getMno()).memoText(updateText).build());
        testEntityManager.clear();

        Optional<Memo> byId = memoRepository.findById(memo.getMno());
        Assertions.assertTrue(byId.isPresent());

        Memo inquiryMemo = byId.get();
        Assertions.assertEquals(updateText,inquiryMemo.getMemoText());
    }
}