package com.example.demo.repository.movie;

import com.example.demo.entity.movie.MovieImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieImageRepository extends JpaRepository<MovieImage,Long> {
}
