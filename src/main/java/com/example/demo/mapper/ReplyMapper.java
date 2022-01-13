package com.example.demo.mapper;

import com.example.demo.dto.ReplyDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.Reply;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ReplyMapper {

    @Mapping(target = "board", expression = "java(makeBoard(replyDTO.getBno()))")
    Reply dtoToEntity(ReplyDTO replyDTO);

    @Mapping(target = "bno", expression = "java(reply.getBoard().getBno())")
    ReplyDTO entityToDTO(Reply reply);

    default Board makeBoard(Long bno){
        return new Board(bno);
    }
}
