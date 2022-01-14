package com.example.demo.util;

import com.example.demo.entity.Board;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.UUID;

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

    @Test
    @DisplayName("100개의 Movie data insert sql을 작성한다.")
    @Disabled
    void makeMovieDummy() throws FileNotFoundException {
        try(PrintWriter pw=new PrintWriter("src/main/resources/dummy-movie.sql")){

            for(int i=1; i<=100; i++){
                String data = "INSERT INTO movie (mno, moddate, regdate, title) VALUES (" + i + ", NOW(), NOW(), 'Movie...." + i + "');";
                pw.println(data);
            }
        }
    }

    @Test
    @DisplayName("100개의 Movie data의 연관된 Movie Image를 만드는 sql을 작성한다.")
    @Disabled
    void makeMovieImageDummy() throws FileNotFoundException {
        try(PrintWriter pw=new PrintWriter("src/main/resources/dummy-movie-image.sql")){

            int moviePrimaryNumber = 0;
            for(int i=1; i<=100; i++){

                int count = (int) (Math.random() * 5) + 1;

                for(int j=0; j<count; j++){
                    String uuid = UUID.randomUUID().toString();
                    String data = "INSERT INTO movie_image (inum, img_name, uuid, movie_mno) VALUES (" + (++moviePrimaryNumber) + ", 'test" + j + ".jpg', '" + uuid + "', " + i + ");";
                    pw.println(data);
                }
            }
        }
    }

    @Test
    @DisplayName("영화 도메인의 Member엔티티를 100명 삽입하는 insert문을 작성한다.")
    @Disabled
    void makeMovieMemberDummy() throws FileNotFoundException {
        try(PrintWriter pw=new PrintWriter("src/main/resources/dummy-movie-member.sql")){
            for(int i=1; i<=100; i++){
                String data = "INSERT INTO m_member (mid, moddate, regdate, email, nickname, pw) VALUES (" + i + ", NOW(), NOW(), 'r" + i + "zerock.org', 'reviewer" + i + "', '1111');";
                pw.println(data);
            }
        }
    }

    @Test
    @DisplayName("영화 도메인의 Review 엔티티를 200개 삽입하는 sql파일을 작성한다.")
    @Disabled
    void makeMovieReviewDummy() throws FileNotFoundException {

        try(PrintWriter pw=new PrintWriter("src/main/resources/dummy-movie-review.sql")){
            for(int i=1; i<=200; i++){
                Long mno = (long) (Math.random() * 100) + 1;
                Long mid = ((long) (Math.random() * 100) + 1);
                int grade = (int) (Math.random() * 5) + 1;
                String text = "이 영화에 대한 느낌..." + i;

                String data = "INSERT INTO review (reviewnum, moddate, regdate, grade, text, member_mid, movie_mno) VALUES (" +
                        i + ", NOW(), NOW(), " + grade + ", '" + text + "', " + mno + ", " + mid + ");";

                pw.println(data);
            }
        }
    }
}
