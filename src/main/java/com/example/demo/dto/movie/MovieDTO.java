package com.example.demo.dto.movie;

import com.example.demo.entity.movie.MovieImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {

    private Long mno;

    private String title;

    private List<MovieImageDTO> imageDTOList = new ArrayList<>();

    private double avg;

    private int reviewCnt;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

}
