package com.example.demo.service.movie;

import com.example.demo.dto.movie.MovieDTO;
import com.example.demo.repository.movie.MovieRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Long register(MovieDTO movieDTO) {
        return null;
    }
}
