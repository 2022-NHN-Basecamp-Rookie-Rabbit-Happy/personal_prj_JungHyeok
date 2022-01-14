package com.example.demo.dto.movie;

import com.example.demo.entity.movie.MovieImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
