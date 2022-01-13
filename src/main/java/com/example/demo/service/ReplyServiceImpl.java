package com.example.demo.service;

import com.example.demo.configuration.BaseCampException;
import com.example.demo.dto.ReplyDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.Reply;
import com.example.demo.enums.ErrorCode;
import com.example.demo.mapper.ReplyMapper;
import com.example.demo.repository.ReplyRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    public ReplyServiceImpl(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    @Override
    public Long register(ReplyDTO replyDTO) {

        Reply reply = Mappers.getMapper(ReplyMapper.class).dtoToEntity(replyDTO);

        replyRepository.save(reply);

        return reply.getRno();
    }

    @Override
    public List<ReplyDTO> getList(Long bno) {
        List<Reply> result = replyRepository.getRepliesByBoardOrderByRno(new Board(bno));
        return result.stream().map(reply -> Mappers.getMapper(ReplyMapper.class).entityToDTO(reply))
                .collect(Collectors.toList());
    }

    @Override
    public void modify(ReplyDTO replyDTO) {
        replyRepository.findById(replyDTO.getRno())
                .orElseThrow(() -> new BaseCampException(ErrorCode.REPLY_NOT_FOUND));

        Reply reply= Mappers.getMapper(ReplyMapper.class).dtoToEntity(replyDTO);

        replyRepository.save(reply);
    }

    @Override
    public void remove(Long rno) {

        Reply reply = replyRepository.findById(rno).orElseThrow(() -> new BaseCampException(ErrorCode.REPLY_NOT_FOUND));

        replyRepository.delete(reply);
    }
}
