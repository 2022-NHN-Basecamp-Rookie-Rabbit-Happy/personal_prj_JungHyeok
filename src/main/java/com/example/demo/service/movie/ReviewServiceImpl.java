package com.example.demo.service.movie;

import com.example.demo.configuration.BaseCampException;
import com.example.demo.dto.movie.ReviewDTO;
import com.example.demo.entity.movie.Movie;
import com.example.demo.entity.movie.Review;
import com.example.demo.enums.ErrorCode;
import com.example.demo.mapper.movie.ReviewMapper;
import com.example.demo.repository.movie.ReviewRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<ReviewDTO> getListOfMovie(Long mno) {

        Movie movie = new Movie(mno);

        List<Review> result = reviewRepository.findByMovie(movie);

        ReviewMapper reviewMapper = Mappers.getMapper(ReviewMapper.class);

        return result.stream().map(reviewMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public Long register(ReviewDTO movieReviewDTO) {

        ReviewMapper reviewMapper = Mappers.getMapper(ReviewMapper.class);
        Review review = reviewMapper.dtoToEntity(movieReviewDTO);
        reviewRepository.save(review);

        return review.getReviewnum();
    }

    @Override
    public void modify(ReviewDTO movieReviewDTO) {
        Review review = reviewRepository.findById(movieReviewDTO.getMno()).orElseThrow(()
                -> new BaseCampException(ErrorCode.REVIEW_NOT_FOUND));

        review.changeGrade(movieReviewDTO.getGrade());
        review.changeText(movieReviewDTO.getText());

        reviewRepository.save(review);
    }

    @Override
    public void remove(Long reviewNum) {
        Review review = reviewRepository.findById(reviewNum).orElseThrow(()
                -> new BaseCampException(ErrorCode.REVIEW_NOT_FOUND));

        reviewRepository.delete(review);
    }
}
