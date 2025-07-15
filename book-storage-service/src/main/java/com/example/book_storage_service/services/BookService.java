package com.example.book_storage_service.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

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
        else throw new RuntimeException("Book with isbn " + isbn + " not found");
    }

    public Book bookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) return book.get();
        else throw new RuntimeException("Book with id " + id + " not found");
    }

    public List<Book> allBooks() {
        return bookRepository.findAll();
    }

    public Book addBook(Book book) {
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new RuntimeException("Book with ISBN " + book.getIsbn() + " already exists");
        }
        return bookRepository.save(book);
    }

    public Book editBook(Long id, Book book) {
        Optional<Book> existBook = bookRepository.findById(id);
        if (!existBook.isPresent()) throw new RuntimeException("Book with id " + id + " not found");
        
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
