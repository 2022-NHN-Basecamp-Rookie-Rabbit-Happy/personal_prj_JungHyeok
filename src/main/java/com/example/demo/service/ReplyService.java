package com.example.demo.service;

import com.example.demo.dto.ReplyDTO;

import java.util.List;

public interface ReplyService {
    Long register(ReplyDTO replyDTO);

    List<ReplyDTO> getList(Long bno);

    void modify(ReplyDTO replyDTO);

    void remove(Long rno);
}
