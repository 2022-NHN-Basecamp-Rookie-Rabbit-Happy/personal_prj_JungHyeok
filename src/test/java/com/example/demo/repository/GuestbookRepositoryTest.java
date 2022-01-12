package com.example.demo.repository;

import com.example.demo.entity.GuestBook;
import com.example.demo.entity.QGuestBook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@DataJpaTest
@Sql("/dummy.sql")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //DataJpa의 기본 내장 메모리 디비로 바꿔치지 않겠다.
class GuestbookRepositoryTest {

    @Autowired
    GuestbookRepository guestbookRepository;

    @Autowired
    TestEntityManager testEntityManager;

    private final Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
    private final String keyword = "1";
    private final QGuestBook qGuestBook = QGuestBook.guestBook;
    @Test
    @DisplayName("dummy.sql 삽입테스트")
    void test(){
        List<GuestBook> all = guestbookRepository.findAll();
        Assertions.assertEquals(300,all.size());
    }

    @Test
    @DisplayName("Update 테스트")
    void updateTest(){

        final String changedTitle = "Changed Title...";
        final String changedContent = "Changed Content...";
        Long guestBookPk = 300L;

        Optional<GuestBook> byId = guestbookRepository.findById(guestBookPk);
        Assertions.assertTrue(byId.isPresent());
        GuestBook guestBook = byId.get();

        guestBook.changeTitle(changedTitle);
        guestBook.changeContent(changedContent);

        guestbookRepository.save(guestBook);
        testEntityManager.flush();
        testEntityManager.clear();

        GuestBook inquiryGuestBook = guestbookRepository.findById(guestBookPk).get();

        Assertions.assertEquals(inquiryGuestBook.getContent(),changedContent);
        Assertions.assertEquals(inquiryGuestBook.getTitle(),changedTitle);
    }

    @Test
    @DisplayName("단일항목 테스트")
    void testQuery1(){
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = qGuestBook.title.contains(keyword);
        builder.and(expression);

        List<GuestBook> result = guestbookRepository.findAll(builder, pageable).getContent();

        result.forEach(guestBook -> Assertions.assertTrue(guestBook.getTitle().contains(keyword)));
    }

    @Test
    @DisplayName("다중항목 검색테스트")
    void testQuery2(){
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression exTitle = qGuestBook.title.contains(keyword);
        BooleanExpression exContent = qGuestBook.content.contains(keyword);
        BooleanExpression exAll = exContent.or(exContent);
        builder.and(exAll).and(qGuestBook.gno.gt(0L));

        List<GuestBook> content = guestbookRepository.findAll(builder, pageable).getContent();

        content.forEach(guestBook -> {
            String guestBookContent = guestBook.getContent();
            String guestBookTitle = guestBook.getTitle();
            Long gno = guestBook.getGno();
            Assertions.assertTrue(containsKeywordInTitleAndContent(guestBookTitle, guestBookContent, keyword));
            Assertions.assertTrue(gnoGreaterThanZero(gno));
        });

    }

    private boolean containsKeywordInTitleAndContent(String title,String content, String keyword){
        return title.contains(keyword) && content.contains(keyword);
    }

    private boolean gnoGreaterThanZero(Long gno){
        return gno > 0;
    }
}