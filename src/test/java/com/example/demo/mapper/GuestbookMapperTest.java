package com.example.demo.mapper;

import com.example.demo.dto.GuestbookDTO;
import com.example.demo.entity.GuestBook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class GuestbookMapperTest {

    @Test
    @DisplayName("Mapper Test - dtoToEntity")
    void dtoToEntity(){
        GuestbookMapper mapper = Mappers.getMapper(GuestbookMapper.class);
        GuestbookDTO guestbookDTO = new GuestbookDTO(1L, "title", "content", "writer",
                LocalDateTime.now(), LocalDateTime.now());
        GuestBook guestBook = mapper.dtoToEntity(guestbookDTO);

        Assertions.assertEquals(guestBook.getTitle(), guestbookDTO.getTitle());
        Assertions.assertEquals(guestBook.getContent(), guestbookDTO.getContent());
        Assertions.assertEquals(guestBook.getTitle(), guestbookDTO.getTitle());
        Assertions.assertEquals(guestBook.getGno(), guestbookDTO.getGno());
        Assertions.assertEquals(guestBook.getModDate(), guestbookDTO.getModDate());
        Assertions.assertEquals(guestBook.getRegDate(), guestbookDTO.getRegDate());
    }

    @Test
    @DisplayName("Mapper Test - entityToDto")
    void entityToDto(){
        GuestbookMapper mapper = Mappers.getMapper(GuestbookMapper.class);
        GuestBook guestBook = new GuestBook(1L, "title", "content", "writer");
        GuestbookDTO guestbookDTO = mapper.entityToDto(guestBook);

        Assertions.assertEquals(guestbookDTO.getContent(),guestBook.getContent());
        Assertions.assertEquals(guestbookDTO.getTitle(),guestBook.getTitle());
        Assertions.assertEquals(guestbookDTO.getWriter(),guestBook.getWriter());
    }
}