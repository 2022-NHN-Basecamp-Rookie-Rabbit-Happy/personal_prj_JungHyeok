package com.example.demo.mapper;

import com.example.demo.dto.GuestbookDTO;
import com.example.demo.entity.GuestBook;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface GuestbookMapper {
    GuestBook dtoToEntity(GuestbookDTO dto);
    GuestbookDTO entityToDto(GuestBook entity);
}
