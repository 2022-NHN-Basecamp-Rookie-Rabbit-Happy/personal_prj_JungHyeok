package com.example.demo.repository;

import com.example.demo.entity.GuestBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestbookRepository extends JpaRepository<GuestBook,Long> {
}
