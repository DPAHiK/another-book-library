package com.example.book_storage_service.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.book_storage_service.models.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
    Optional<Book> findByIsbn(String isbn);
    boolean existsByIsbn(String isbn);
}
