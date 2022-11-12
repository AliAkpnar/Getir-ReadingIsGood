package com.getir.readingisgood.persistence.repository;

import com.getir.readingisgood.persistence.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
}
