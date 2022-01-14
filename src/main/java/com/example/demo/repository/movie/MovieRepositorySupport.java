package com.example.demo.repository.movie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieRepositorySupport {

    Page<Object[]> getListPage(Pageable pageable);
}
