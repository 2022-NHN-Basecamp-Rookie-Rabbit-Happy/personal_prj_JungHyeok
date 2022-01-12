package com.example.demo.service;

import com.example.demo.dto.GuestbookDTO;
import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResultDTO;
import com.example.demo.entity.GuestBook;
import com.example.demo.mapper.GuestbookMapper;
import com.example.demo.repository.GuestbookRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class GuestbookServiceImpl implements GuestbookService {

    private final GuestbookRepository guestbookRepository;

    public GuestbookServiceImpl(GuestbookRepository guestbookRepository) {
        this.guestbookRepository = guestbookRepository;
    }

    @Override
    public Long register(GuestbookDTO dto) {

        GuestbookMapper mapper = Mappers.getMapper(GuestbookMapper.class);
        GuestBook guestBook = mapper.dtoToEntity(dto);

        guestbookRepository.save(guestBook);
        return guestBook.getGno();
    }

    @Override
    public PageResultDTO<GuestbookDTO, GuestBook> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());
        Page<GuestBook> result = guestbookRepository.findAll(pageable);
        GuestbookMapper mapper = Mappers.getMapper(GuestbookMapper.class);
        Function<GuestBook, GuestbookDTO> fn = mapper::entityToDto;
        return new PageResultDTO<>(result, fn);
    }
}