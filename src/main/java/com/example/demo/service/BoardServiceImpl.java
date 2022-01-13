package com.example.demo.service;

import com.example.demo.configuration.BaseCampException;
import com.example.demo.dto.BoardDTO;
import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResultDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.Member;
import com.example.demo.enums.ErrorCode;
import com.example.demo.mapper.BoardMapper;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.ReplyRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    public BoardServiceImpl(BoardRepository boardRepository,ReplyRepository replyRepository) {
        this.boardRepository = boardRepository;
        this.replyRepository = replyRepository;
    }

    @Override
    public Long register(BoardDTO boardDTO) {
        BoardMapper mapper = Mappers.getMapper(BoardMapper.class);
        Board board = mapper.dtoToEntity(boardDTO);
        boardRepository.save(board);
        return board.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        BoardMapper mapper = Mappers.getMapper(BoardMapper.class);
        Function<Object[], BoardDTO> fn = (en -> mapper.entityToDTO((Board) en[0], (Member) en[1], (Long) en[2]));
        Page<Object[]> result =
                boardRepository.getBoardWithReplyCount(pageRequestDTO.getPageable(Sort.by("bno").descending()));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public BoardDTO get(Long bno) {
        Object result = boardRepository.getBoardByBno(bno);
        Object[] arr = (Object[]) result;
        return Mappers.getMapper(BoardMapper.class).entityToDTO((Board) arr[0], (Member) arr[1], (Long) arr[2]);
    }

    @Override
    @Transactional
    public void removeWithReplies(Long bno) {
        Board board = boardRepository.findById(bno).orElseThrow(() -> new BaseCampException(ErrorCode.BOARD_NOT_FOUND));

        replyRepository.deleteByBno(bno);
        boardRepository.delete(board);
    }

    @Override
    public void modify(BoardDTO boardDTO) {

        Board board = boardRepository.findById(boardDTO.getBno()).orElseThrow(()
                -> new BaseCampException(ErrorCode.BOARD_NOT_FOUND));

        board.changeTitle(boardDTO.getTitle());
        board.changeContent(boardDTO.getContent());
        boardRepository.save(board);
    }
}
