package com.example.demo.repository.movie;

import com.example.demo.entity.Board;
import com.example.demo.entity.movie.*;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

public class MovieRepositorySupportImpl extends QuerydslRepositorySupport implements MovieRepositorySupport {

    public MovieRepositorySupportImpl() {
        super(Movie.class);
    }

    @Override
    public Page<Object[]> getListPage(Pageable pageable) {
        QMovie movie = QMovie.movie;
        QReview review = QReview.review;
        QMovieImage movieImage = QMovieImage.movieImage;


        JPQLQuery<MovieImage> query = from(movieImage);
        query.leftJoin(movie).on(movie.eq(movieImage.movie));
        JPQLQuery<Tuple> tuple = query
                .select(movie, movieImage,
                        JPAExpressions.select(review.countDistinct()).from(review).where(review.movie.eq(movieImage.movie)),
                        JPAExpressions.select(review.grade.avg()).from(review).where(review.movie.eq(movieImage.movie))
                )
                .where(movieImage.inum.in(JPAExpressions.select(movieImage.inum.min()).from(movieImage).where(movie.eq(movieImage.movie))
                        .groupBy(movie, movieImage.movie))
                );


        Sort sort = pageable.getSort();

        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();

            PathBuilder orderByExpression = new PathBuilder(Movie.class, "movie");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });

        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();
        long count = tuple.fetchCount();

        return new PageImpl<>(
                result.stream().map(Tuple::toArray).collect(Collectors.toList()), pageable, count
        );
    }
}
