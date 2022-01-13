package com.example.demo.util;

import com.example.demo.entity.Board;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class MakeSql {

    @Test
    @DisplayName("300개의 더미데이터 테스를 만들 파일 입출력을 작성한다 - 파트 2 의 방명록")
    @Disabled
//  주석주의
//INSERT INTO guest_book (gno,moddate,regdate,content,title,writer) VALUE (1, NOW(), NOW(), 'Content...1', 'Title...1', 'user1');
    void makeSql() throws FileNotFoundException {
        try(PrintWriter pw=new PrintWriter("src/main/resources/dummy.sql")){

            for(int i=1; i<=300; i++){
                String data = "INSERT INTO guest_book (gno, moddate, regdate, content, title, writer) VALUES (" + i + ", NOW(), NOW(), 'Content..." + i + "', 'Title..."+i+"', " + "'user" + i%10 + "');";
//                String data = "INSERT INTO guest_book (gno) VALUE (" + i + ")";
                pw.println(data);
            }
        }
    }

    @Test
    @DisplayName("100개의 Member 더미데이터를 작성한다.")
    @Disabled
    void makeMemberDummy() throws FileNotFoundException {
        try(PrintWriter pw=new PrintWriter("src/main/resources/dummy-member.sql")){

            for(int i=1; i<=100; i++){
                String data = "INSERT INTO member (email, name, password, moddate, regdate) VALUES ('user" + i + "@aaa.com', 'USER" + i + "', '1111', NOW(), NOW());";
                pw.println(data);
            }
        }
    }

    @Test
    @DisplayName("100개의 게시물 insert sql을 작성한다.")
    @Disabled
    void makeBoardDummy() throws FileNotFoundException {
        try(PrintWriter pw=new PrintWriter("src/main/resources/dummy-board.sql")){

            for(int i=1; i<=100; i++){
                String userEmail = "user" + i + "@aaa.com";
                String data = "INSERT INTO board (bno, content, title, writer_email, moddate, regdate) VALUES (" + i + ", 'Content..." + i + "', 'Title..." + i + "', '" + userEmail + "', NOW(), NOW());";
                pw.println(data);
            }
        }
    }

    @Test
    @DisplayName("300개의 reply data insert sql을 작성한다.")
    @Disabled
    void makeReplyDummy() throws FileNotFoundException {
        try(PrintWriter pw=new PrintWriter("src/main/resources/dummy-reply.sql")){
            for(int i=1; i<=300; i++) {
                long bno = (long) (Math.random() * 100) + 1;
                String data = "INSERT INTO reply (rno, moddate, regdate, replyer, text, board_bno) VALUES (" + i + ", NOW(), NOW(), 'guest', 'Reply......" + i + "', " + bno + ");";
                pw.println(data);
            }
        }
    }
}
