package com.example.demo.repository.movie;

import com.example.demo.entity.movie.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "MovieMemberRepository")
public interface MemberRepository extends JpaRepository<Member, Long> {
}
