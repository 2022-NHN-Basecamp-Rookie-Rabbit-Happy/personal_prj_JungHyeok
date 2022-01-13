package com.example.demo.service;

import com.example.demo.configuration.BaseCampException;
import com.example.demo.dto.GuestbookDTO;
import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResultDTO;
import com.example.demo.entity.GuestBook;
import com.example.demo.entity.QGuestBook;
import com.example.demo.enums.ErrorCode;
import com.example.demo.mapper.GuestbookMapper;
import com.example.demo.repository.GuestbookRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
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
        BooleanBuilder booleanBuilder = getSearch(requestDTO);
        Page<GuestBook> result = guestbookRepository.findAll(booleanBuilder,pageable);
        GuestbookMapper mapper = Mappers.getMapper(GuestbookMapper.class);
        Function<GuestBook, GuestbookDTO> fn = mapper::entityToDto;
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public GuestbookDTO read(Long gno) {
        GuestBook guestBook = guestbookRepository.findById(gno).orElse(null);
        return Mappers.getMapper(GuestbookMapper.class).entityToDto(guestBook);
    }

    @Override
    public void remove(Long gno) {
        GuestBook guestBook =
                guestbookRepository.findById(gno).orElseThrow(()
                        -> new BaseCampException(ErrorCode.GUESTBOOK_NOT_FOUND));

        guestbookRepository.delete(guestBook);
    }

    @Override
    public void modify(GuestbookDTO dto) {
        GuestBook guestBook = guestbookRepository.findById(dto.getGno()).orElseThrow(()
                -> new BaseCampException(ErrorCode.GUESTBOOK_NOT_FOUND));

        guestBook.changeTitle(dto.getTitle());
        guestBook.changeContent(dto.getContent());
        guestbookRepository.save(guestBook);
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO){
        String type = requestDTO.getType();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QGuestBook qGuestBook = QGuestBook.guestBook;
        String keyword = requestDTO.getKeyword();

        BooleanExpression expression = qGuestBook.gno.gt(0L);

        booleanBuilder.and(expression);

        if(type==null || type.trim().length()==0){
            return booleanBuilder;
        }

        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if(type.contains("t")){
            conditionBuilder.or(qGuestBook.title.contains(keyword));
        }
        if(type.contains("c")){
            conditionBuilder.or(qGuestBook.content.contains(keyword));
        }
        if(type.contains("w")){
            conditionBuilder.or(qGuestBook.writer.contains(keyword));
        }

        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }
}
