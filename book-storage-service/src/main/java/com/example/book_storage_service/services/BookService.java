package com.example.book_storage_service.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.book_storage_service.dto.BookAddDto;
import com.example.book_storage_service.dto.BookUpdateDto;
import com.example.book_storage_service.exception.CustomHttpException;
import com.example.book_storage_service.models.Book;
import com.example.book_storage_service.mapper.BookMapper;
import com.example.book_storage_service.repo.BookRepository;

@Service
public class BookService {

    final private BookRepository bookRepository;

    final private BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public Book bookByIsbn(String isbn) {
        Optional<Book> book = bookRepository.findByIsbn(isbn);
        if (book.isPresent()) return book.get();
        else throw new CustomHttpException("Book with isbn " + isbn + " not found", HttpStatus.NOT_FOUND);
    }

    public Book bookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) return book.get();
        else throw new CustomHttpException("Book with id " + id + " not found", HttpStatus.NOT_FOUND);
    }

    public List<Book> allBooks() {
        return bookRepository.findAll();
    }

    public Book addBook(BookAddDto book) {
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new CustomHttpException("Book with ISBN " + book.getIsbn() + " already exists", HttpStatus.CONFLICT);
        }

        Book newBook = new Book.builder()
                            .isbn(book.getIsbn())
                            .title(book.getTitle())
                            .author(book.getAuthor())
                            .description(book.getDescription())
                            .genre(book.getGenre())
                            .build();

        return bookRepository.save(newBook);
    }

    public Book editBook(Long id, BookUpdateDto bookData) {
        Optional<Book> existBook = bookRepository.findById(id);
        if (!existBook.isPresent()) throw new CustomHttpException("Book with id " + id + " not found", HttpStatus.NOT_FOUND);
        
        Book newBook = existBook.get();
        bookMapper.updateBook(bookData, newBook);
        return bookRepository.save(newBook);
    }

    public int deleteBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);

        if (book.isPresent()) {
            bookRepository.deleteById(id);
            return 1;
        }

        return 0;
    }
}
