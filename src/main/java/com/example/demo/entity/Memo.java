package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tbl_memo")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    @Column(length = 200, nullable = false)
    private String memoText;

    public void setMemoText(String memoText) {
        this.memoText = memoText;
    }
}
