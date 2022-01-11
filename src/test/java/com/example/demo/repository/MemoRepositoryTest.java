package com.example.demo.repository;

import com.example.demo.entity.Memo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.stream.IntStream;

@DataJpaTest
@ActiveProfiles("test")
class MemoRepositoryTest {

    @Autowired
    MemoRepository memoRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    void MemoRepository가주입되는지_확인한다(){
        Assertions.assertNotNull(memoRepository);
        System.out.println(memoRepository.getClass().getName());
    }

    @Test
    void testInsertDummies(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Memo memo=Memo.builder().memoText("Sample..."+i).build();
            memoRepository.save(memo);
        });

        Assertions.assertEquals(100, memoRepository.findAll().size());
    }

    @Test
    void testFindById_저장을한이후에_isPresent는_true이여야한다(){
        Memo memo=Memo.builder().memoText("textMessage").build();
        memoRepository.save(memo);

        Optional<Memo> byId = memoRepository.findById(memo.getMno());
        Assertions.assertTrue(byId.isPresent());
    }

    @Test
    void testFindById_저장되어있지않은Id로_findById를할경우_isEmpty가true이여야한다(){
        Optional<Memo> byId = memoRepository.findById(-1L);
        Assertions.assertTrue(byId.isEmpty());
    }

    @Test
    @DisplayName("저장 후, flush, clear , findById를 통한 업데이트 테스트")
    void testUpdate(){
        Memo memo = Memo.builder().memoText("original Text").build();
        testEntityManager.persist(memo);

        memo.setMemoText("Update Text");

        testEntityManager.flush();
        testEntityManager.clear();

        Optional<Memo> byId =memoRepository.findById(memo.getMno());
        Assertions.assertTrue(byId.isPresent());
        Memo updatedMemo = byId.get();

        Assertions.assertEquals(memo.getMemoText(),updatedMemo.getMemoText());
    }

    @Test
    @DisplayName("엔티티 삭제 테스트 - 실패(데이타베이스에 pk가 존재하지 않을경우)")
    void deleteTest1(){
        Assertions.assertThrows(EmptyResultDataAccessException.class,()->memoRepository.deleteById(-1L));
    }

    @Test
    @DisplayName("엔티티 삭제 테스트 - 성공(엔티티가 존재할경우)")
    void deleteTest2(){
        Memo memo = new Memo();
        memo.setMemoText("textMemo");

        memoRepository.save(memo);

        testEntityManager.clear();

        //flush 된 영속성 컨텍스트에서 다시 엔티티를 찾아온다.

        Memo find = memoRepository.findById(memo.getMno()).get();
        memoRepository.deleteById(find.getMno());

        Assertions.assertEquals(0, memoRepository.findAll().size());
    }
}