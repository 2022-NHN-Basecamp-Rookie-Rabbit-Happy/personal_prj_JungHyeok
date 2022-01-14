package com.example.demo.entity.movie;

import com.example.demo.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "MovieMember")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "m_member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mid;

    private String email;

    private String pw;

    private String nickname;

    public Member(Long mid) {
        this.mid = mid;
    }
}
