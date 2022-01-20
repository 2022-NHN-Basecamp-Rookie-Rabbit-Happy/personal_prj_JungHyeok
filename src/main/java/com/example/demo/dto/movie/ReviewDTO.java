package com.example.demo.dto.movie;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReviewDTO {

    private Long reviewnum;

    private Long mno;

    private Long mid;

    private String nickname;

    private String email;

    private int grade;

    private String text;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

}
