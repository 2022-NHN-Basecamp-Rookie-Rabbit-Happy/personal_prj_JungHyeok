package com.example.demo.service;

import com.example.demo.configuration.BaseCampException;
import com.example.demo.dto.GuestbookDTO;
import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResultDTO;
import com.example.demo.entity.GuestBook;
import com.example.demo.enums.ErrorCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.LongStream;

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

    @Test
    @DisplayName("guestbookService의 read 메서드를 테스트한다.")
    void testRead(){
        GuestbookDTO guestbookDTO = makeDTO("title", "content", "writer");
        Long register = guestbookService.register(guestbookDTO);

        GuestbookDTO read = guestbookService.read(register);
        Assertions.assertEquals(read.getTitle(),guestbookDTO.getTitle());
        Assertions.assertEquals(read.getWriter(),guestbookDTO.getWriter());
        Assertions.assertEquals(read.getContent(),guestbookDTO.getContent());
    }

    @Test
    @DisplayName("guestbookService의 read 메서드 테스트 - 등록되어 있지 않은 것을 읽어올 시 null을 반환한다.")
    void testRead2(){
        GuestbookDTO read = guestbookService.read(-1L);
        Assertions.assertNull(read);
    }

    @Test
    @DisplayName("guestbookService의 remove 테스트 - 실패")
    void removeTest(){
        BaseCampException baseCampException =
                Assertions.assertThrows(BaseCampException.class, () -> guestbookService.remove(-1L));

        Assertions.assertEquals(baseCampException.getErrorCode().getDetail(), ErrorCode.GUESTBOOK_NOT_FOUND.getDetail());
        Assertions.assertEquals(baseCampException.getErrorCode().getHttpStatus(), ErrorCode.GUESTBOOK_NOT_FOUND.getHttpStatus());

    }

    @Test
    @DisplayName("guestBookService의 remove 테스트 - 성공 ")
    void removeTestSuccess(){
        GuestbookDTO guestbookDTO = makeDTO("dummy Title", "dummy Content", "dummy Writer");
        Long register = guestbookService.register(guestbookDTO);

        guestbookService.remove(register);
    }

    @Test
    @DisplayName("guestBookService의 modify 테스트 - 실패 (pk가 존재하지 않는경우)")
    void modifyTestFail(){
        BaseCampException baseCampException = Assertions.assertThrows(BaseCampException.class, () -> guestbookService.modify(new GuestbookDTO(-1L, "title", "content", "wrtier"
                , LocalDateTime.now(), LocalDateTime.now())));

        Assertions.assertEquals(baseCampException.getErrorCode().getDetail(), ErrorCode.GUESTBOOK_NOT_FOUND.getDetail());
        Assertions.assertEquals(baseCampException.getErrorCode().getHttpStatus(), ErrorCode.GUESTBOOK_NOT_FOUND.getHttpStatus());
    }

    @Test
    @DisplayName("guestBookService의 modify 테스트 성공")
    void modifyTestSuccess(){
        GuestbookDTO guestbookDTO = makeDTO("dummy Title", "dummy Content", "dummy Writer");
        Long register = guestbookService.register(guestbookDTO);
        GuestbookDTO guestbookDTOModify = new GuestbookDTO();
        guestbookDTOModify.setGno(register);
        guestbookDTOModify.setTitle("modify Title");
        guestbookDTOModify.setContent("modify Content");
        guestbookService.modify(guestbookDTOModify);

        GuestbookDTO read = guestbookService.read(register);
        Assertions.assertEquals(read.getContent(),guestbookDTOModify.getContent());
        Assertions.assertEquals(read.getTitle(),guestbookDTOModify.getTitle());
        Assertions.assertEquals(read.getGno(),guestbookDTOModify.getGno());
    }



    private GuestbookDTO makeDTO(String title, String content, String writer){
        GuestbookDTO guestbookDTO = new GuestbookDTO();
        guestbookDTO.setTitle(title);
        guestbookDTO.setContent(content);
        guestbookDTO.setWriter(writer);
        return guestbookDTO;
    }
}