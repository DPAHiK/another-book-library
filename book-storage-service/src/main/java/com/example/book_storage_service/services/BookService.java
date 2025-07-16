package com.example.book_storage_service.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.book_storage_service.dto.BookAddRequest;
import com.example.book_storage_service.dto.BookEditRequest;
import com.example.book_storage_service.exception.CustomHttpException;
import com.example.book_storage_service.models.Book;
import com.example.book_storage_service.repo.BookRepository;

@Service
public class BookService {

    final private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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

    public Book addBook(BookAddRequest book) {
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

    public Book editBook(Long id, BookEditRequest book) {
        Optional<Book> existBook = bookRepository.findById(id);
        if (!existBook.isPresent()) throw new CustomHttpException("Book with id " + id + " not found", HttpStatus.NOT_FOUND);
        
        Book newBook = existBook.get();
        if(book.getAuthor() != null) newBook.setAuthor(book.getAuthor());
        if(book.getDescription() != null) newBook.setDescription(book.getDescription());
        if(book.getGenre() != null) newBook.setGenre(book.getGenre());
        if(book.getIsbn() != null) newBook.setIsbn(book.getIsbn());
        if(book.getTitle() != null) newBook.setTitle(book.getTitle());

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
