package com.example.demo.service.movie;

import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResultDTO;
import com.example.demo.dto.movie.MovieDTO;
import com.example.demo.dto.movie.MovieImageDTO;
import com.example.demo.entity.movie.Movie;
import com.example.demo.entity.movie.MovieImage;
import com.example.demo.mapper.movie.MovieMapper;
import com.example.demo.repository.movie.MovieImageRepository;
import com.example.demo.repository.movie.MovieRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieImageRepository movieImageRepository;

    public MovieServiceImpl(MovieRepository movieRepository, MovieImageRepository movieImageRepository) {
        this.movieRepository = movieRepository;
        this.movieImageRepository = movieImageRepository;
    }

    @Override
    @Transactional
    public Long register(MovieDTO movieDTO) {

        MovieMapper movieMapper = Mappers.getMapper(MovieMapper.class);
        Movie movie = movieMapper.extractMovieFromMovieDTO(movieDTO);
        List<MovieImage> collect = movieDTO.getImageDTOList().stream().map(movieImageDTO -> movieMapper.movieImageDTOToEntity(movie, movieImageDTO))
                .collect(Collectors.toList());

        movieRepository.save(movie);
        movieImageRepository.saveAll(collect);

        return movie.getMno();
    }

    @Override
    public PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("mno").descending());
        Page<Object[]> result = movieRepository.getListPage(pageable);

        MovieMapper movieMapper = Mappers.getMapper(MovieMapper.class);

        Function<Object[], MovieDTO> fn = (arr -> movieMapper
                .entitiesToDTO((Movie) arr[0], List.of(movieMapper.movieImageEntityToDTO((MovieImage) arr[1])),
                        (Double) arr[2], (Long) arr[3]));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public MovieDTO getMovie(Long mno) {
        List<Object[]> result = movieRepository.getMovieWithAll(mno);
        Movie movie = (Movie) result.get(0)[0];

        MovieMapper movieMapper = Mappers.getMapper(MovieMapper.class);

        List<MovieImageDTO> collect
                = result.stream().map(arr -> movieMapper.movieImageEntityToDTO((MovieImage) arr[1]))
                .collect(Collectors.toList());

        Double avg = (Double) result.get(0)[2];
        Long reviewCnt = (Long) result.get(0)[3];

        return movieMapper.entitiesToDTO(movie, collect, avg, reviewCnt);
    }
}
