package com.example.demo.service;

import com.example.demo.dto.GuestbookDTO;
import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResultDTO;
import com.example.demo.entity.GuestBook;
import com.example.demo.mapper.GuestbookMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class GuestbookServiceImplTest {

    @Autowired
    private GuestbookService guestbookService;

    @Test
    @DisplayName("guestbookService의 register 메서드를 테스트한다. - 성공")
    void registerTestSuccess(){

        GuestbookDTO guestbookDTO = makeDTO("Sample Title", "Sample Content", "user0");
        Long register = guestbookService.register(guestbookDTO);
        Assertions.assertNotNull(register);
    }

    @Test
    @DisplayName("guestbookService의 getList 메서드를 테스트한다.")
    void getListTest(){
        LongStream.rangeClosed(1,300).forEach(i-> {
            GuestbookDTO guestbookDTO = makeDTO("SampleTitle..." + i, "SampleContent..." + i, "user" + i % 10);
            guestbookService.register(guestbookDTO);
        });

        PageRequestDTO pageRequestDTO = new PageRequestDTO(1, 10);
        PageResultDTO<GuestbookDTO, GuestBook> resultDTO = guestbookService.getList(pageRequestDTO);
        Assertions.assertFalse(resultDTO.isPrev());
        Assertions.assertTrue(resultDTO.isNext());
        Assertions.assertEquals(30, resultDTO.getTotalPage());
    }

    private GuestbookDTO makeDTO(String title, String content, String writer){
        GuestbookDTO guestbookDTO = new GuestbookDTO();
        guestbookDTO.setTitle(title);
        guestbookDTO.setContent(content);
        guestbookDTO.setWriter(writer);
        return guestbookDTO;
    }
}