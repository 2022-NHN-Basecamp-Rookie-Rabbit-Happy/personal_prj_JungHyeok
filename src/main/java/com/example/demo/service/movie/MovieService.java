package com.example.demo.service.movie;

import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResultDTO;
import com.example.demo.dto.movie.MovieDTO;

public interface MovieService {

    Long register(MovieDTO movieDTO);

    PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO requestDTO);

    MovieDTO getMovie(Long mno);
}
