package com.example.demo.mapper.movie;

import com.example.demo.dto.movie.ReviewDTO;
import com.example.demo.entity.movie.Member;
import com.example.demo.entity.movie.Movie;
import com.example.demo.entity.movie.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(imports = {Movie.class, Member.class})
public interface ReviewMapper {

    @Mapping(target = "movie", expression = "java(new Movie(movieReviewDTO.getMno()))")
    @Mapping(target = "member", expression = "java(new Member(movieReviewDTO.getMid()))")
    Review dtoToEntity(ReviewDTO movieReviewDTO);

    @Mapping(target = "mno", source = "movieReview.movie.mno")
    @Mapping(target = "mid", source = "movieReview.member.mid")
    @Mapping(target = "nickname", source = "movieReview.member.nickname")
    @Mapping(target = "email", source = "movieReview.member.email")
    ReviewDTO entityToDto(Review movieReview);
}
