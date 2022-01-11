package com.example.demo.repository;

import com.example.demo.entity.Memo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@DataJpaTest
@ActiveProfiles("test")
class MemoRepositoryTestForPagingAndSort {

    @Autowired
    MemoRepository memoRepository;

    @BeforeEach
    void setUp(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Memo memo=Memo.builder().memoText("Test Text..."+i).build();
            memoRepository.save(memo);
        });
    }

    @Test
    @DisplayName("페이지네이션 테스트")
    void test1(){
        Page<Memo> result = memoRepository.findAll(PageRequest.of(0, 10));
        System.out.println(result);
        Assertions.assertEquals(10, result.getTotalPages()); //총 몇페이지
        Assertions.assertEquals(100, result.getTotalElements()); //전체 개수
        Assertions.assertEquals(0,result.getNumber()); //페이지 번호 0부터시작
        Assertions.assertEquals(10,result.getSize());// 페이지당 데이터 갯수
        Assertions.assertTrue(result.hasNext());
        Assertions.assertTrue(result.isFirst());

        List<Memo> content = result.getContent();
        content.forEach(this::outputMemo);
    }

    @Test
    @DisplayName("페이지 네이션 + 정렬 테스트 (mno의 역순으로 정렬했기 때문에 앞의 엔티티가 항상 Mno가 높게된다.)")
    void test2(){
        PageRequest mno = PageRequest.of(0, 10, Sort.by("mno").descending());

        List<Memo> content = memoRepository.findAll(mno).getContent();
        compareMnoByDesc(content);
    }

    @Test
    @DisplayName("정렬을 좀 더 복잡하게도 가능하다.")
    void test3(){
        Sort sort1 = Sort.by("mno").descending();
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2);

        PageRequest of = PageRequest.of(0, 10, sortAll);
    }

    @Test
    @DisplayName("쿼리 메서드 테스트 - (findByMemoText)")
    void test4(){
        Optional<Memo> byMemoText = memoRepository.findByMemoText("Test Text...50");
        Assertions.assertTrue(byMemoText.isPresent());

    }

    @Test
    @DisplayName("쿼리 메서드 테스트 - deleteByMemoText")
    void test5(){
        memoRepository.deleteMemoByMemoText("Test Text...50");
        List<Memo> all = memoRepository.findAll();
        Assertions.assertEquals(99,all.size());
    }

    @Test
    @DisplayName("Query 어노테이션 사용")
    void test6(){
        List<Memo> listDesc = memoRepository.getListDesc();
        Assertions.assertEquals(100, listDesc.size());
        compareMnoByDesc(listDesc);
    }

    private void compareMnoByDesc(List<Memo> memoList){
        for(int i=0; i<memoList.size()-1; i++){
            Assertions.assertEquals(1,Long.compare(memoList.get(i).getMno(), memoList.get(i + 1).getMno()));
        }
    }

    private void outputMemo(Memo memo){
        System.out.println(memo.getMno()+", "+memo.getMemoText());
    }
}