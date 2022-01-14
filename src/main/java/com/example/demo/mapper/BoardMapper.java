package com.example.demo.mapper;

import com.example.demo.dto.BoardDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BoardMapper {

    @Mapping(target = "writer", expression = "java(new Member(dto.getWriterEmail()))")
    Board dtoToEntity(BoardDTO dto);

    @Mapping(target = "bno", source = "board.bno")
    @Mapping(target = "title", source = "board.title")
    @Mapping(target = "content", source = "board.content")
    @Mapping(target = "writerEmail",source = "member.email")
    @Mapping(target = "writerName",source = "member.name")
    @Mapping(target = "regDate",expression = "java(board.getRegDate())")
    @Mapping(target = "modDate",expression = "java(board.getModDate())")
    @Mapping(target = "replyCount", expression = "java(replyCount.intValue())")
    BoardDTO entityToDTO(Board board, Member member, Long replyCount);

}
