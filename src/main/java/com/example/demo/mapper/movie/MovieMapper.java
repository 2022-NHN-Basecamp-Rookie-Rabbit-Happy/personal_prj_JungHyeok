package com.example.demo.mapper.movie;

import com.example.demo.dto.movie.MovieDTO;
import com.example.demo.entity.movie.Movie;
import com.example.demo.entity.movie.MovieImage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MovieMapper {

    Movie extractMovieFromMovieDTO(MovieDTO movieDTO);

//    List<MovieImage> extractMovieImageFromMovieDTO(MovieDTO movieDTO);
}
