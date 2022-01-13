package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Member extends BaseEntity{

    @Id
    private String email;

    private String password;

    private String name;

    public Member(String email) {
        this.email = email;
    }
}
