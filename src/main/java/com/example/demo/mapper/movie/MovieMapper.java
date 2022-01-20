package com.example.demo.mapper.movie;

import com.example.demo.dto.movie.MovieDTO;
import com.example.demo.dto.movie.MovieImageDTO;
import com.example.demo.entity.movie.Movie;
import com.example.demo.entity.movie.MovieImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MovieMapper {

    Movie extractMovieFromMovieDTO(MovieDTO movieDTO);

    @Mapping(target = "uuid", source = "movieImageDTO.uuid")
    @Mapping(target = "imgName", source = "movieImageDTO.imgName")
    @Mapping(target = "path", source = "movieImageDTO.path")
    MovieImage movieImageDTOToEntity(Movie movie, MovieImageDTO movieImageDTO);

    MovieImageDTO movieImageEntityToDTO(MovieImage movieImage);

    MovieDTO entitiesToDTO(Movie movie, List<MovieImageDTO> imageDTOList, Double avg, Long reviewCnt);
}
