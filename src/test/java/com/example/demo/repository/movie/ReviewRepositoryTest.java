package com.example.demo.repository.movie;

import com.example.demo.entity.movie.Member;
import com.example.demo.entity.movie.Movie;
import com.example.demo.entity.movie.Review;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql({"/dummy-movie.sql","/dummy-movie-image.sql","/dummy-movie-member.sql", "/dummy-movie-review.sql"})
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void testGetMovieReviews(){

        Movie movie = new Movie(92L);
        List<Review> result = reviewRepository.findByMovie(movie);

        result.forEach(review -> Assertions.assertEquals(92L,review.getMovie().getMno()));

    }

    @Test
    @DisplayName("memberDelete 테스트")
    @Transactional
    @Commit
    void deleteMemberTest(){
        Member member = new Member(1L);

        reviewRepository.deleteByMember(member);
        memberRepository.deleteById(member.getMid());

        entityManager.flush();
        entityManager.clear();

        Optional<Member> byId = memberRepository.findById(1L);
        Assertions.assertTrue(byId.isEmpty());
    }
}