package com.example.demo.service.movie;

import com.example.demo.dto.movie.MovieDTO;
import com.example.demo.dto.movie.MovieImageDTO;
import com.example.demo.entity.movie.Movie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class MovieServiceImplTest {

    @Autowired
    MovieService movieService;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("movieService - register Test")
    void test(){

        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setTitle("title");

        List<MovieImageDTO> movieImageDTOList = new ArrayList<>();
        for(int i=0; i<3; i++){
            MovieImageDTO movieImageDTO = new MovieImageDTO();
            movieImageDTO.setImgName("imgName"+i);
            movieImageDTO.setUuid(UUID.randomUUID().toString());
            movieImageDTO.setPath("Path" + i);
            movieImageDTOList.add(movieImageDTO);
        }

        movieDTO.setImageDTOList(movieImageDTOList);

        Long register = movieService.register(movieDTO);

        entityManager.clear();

        Movie movie = entityManager.find(Movie.class, register);
        Assertions.assertNotNull(movie.getMno());
        Assertions.assertEquals(movie.getMno(),register);
        Assertions.assertEquals(movie.getTitle(),movieDTO.getTitle());

        //Todo : MovieImageRepoisotory에 movieId로 image를 조회할 수 있다면 image들의 assertEquals도 작성해야한다.
    }
}