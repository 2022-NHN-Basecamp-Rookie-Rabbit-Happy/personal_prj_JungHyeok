package com.example.demo.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "tbl_memo")
@Getter
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    @Column(length = 200, nullable = false)
    private String memoText;
}
