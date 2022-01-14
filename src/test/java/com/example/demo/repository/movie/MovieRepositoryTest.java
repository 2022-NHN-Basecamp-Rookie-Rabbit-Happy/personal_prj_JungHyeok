package com.example.demo.repository.movie;

import com.example.demo.entity.movie.Movie;
import com.example.demo.entity.movie.MovieImage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql({"/dummy-movie.sql","/dummy-movie-image.sql","/dummy-movie-member.sql", "/dummy-movie-review.sql"})
class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    @DisplayName("getListPage method를 테스트한다.")
    void testListPage(){
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "mno"));
        Page<Object[]> result = movieRepository.getListPage(pageRequest);
        Assertions.assertEquals(10, result.getTotalPages());
        Assertions.assertEquals(100, result.getTotalElements());

    }

    @Test
    @DisplayName("getMovieWithAll test")
    void testGetMovieWithAll(){
        List<Object[]> movieWithAll = movieRepository.getMovieWithAll(94L);
        for(int i=0; i<movieWithAll.size(); i++){
            Object[] objects = movieWithAll.get(i);
            Movie movie = (Movie) objects[0];
            MovieImage movieImage = (MovieImage) objects[1];
            double grade = (double) objects[2];
            long reviewNum = (long) objects[3];
            Assertions.assertEquals(movie.getMno(),movieImage.getMovie().getMno());
            Assertions.assertTrue(grade>=0.0);
            Assertions.assertTrue(reviewNum>=0);

        }
    }
}