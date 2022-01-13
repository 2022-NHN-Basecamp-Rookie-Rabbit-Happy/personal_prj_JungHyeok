package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReplyDTO {

    private Long rno;
    private String text;
    private String replyer;
    private Long bno;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

}
