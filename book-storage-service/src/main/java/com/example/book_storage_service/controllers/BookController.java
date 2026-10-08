package com.example.book_storage_service.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.book_storage_service.dto.BookAddRequest;
import com.example.book_storage_service.dto.BookEditRequest;
import com.example.book_storage_service.dto.ResponseHandler;
import com.example.book_storage_service.models.Book;
import com.example.book_storage_service.services.BookService;
import com.example.book_storage_service.services.ProducerService;
import static com.example.book_storage_service.services.ProducerService.BOOK_TOPIC;

import jakarta.validation.Valid;


@RestController
public class BookController {

    final private ProducerService producerService;

    final private BookService bookService;

    public BookController(ProducerService producerService, BookService bookService) {
        this.producerService = producerService;
        this.bookService = bookService;
    }

    @GetMapping("/book")
    public ResponseEntity<?> getBooks(){
        return ResponseHandler.generateResponse(HttpStatus.OK, "data", bookService.allBooks());
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<?> bookByID(@PathVariable(value = "id") Long id){
        Book book = bookService.bookById(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, "data", book);
    }

    @GetMapping("/book/isbn/{isbn}")
    public ResponseEntity<?> bookByIsbn(@PathVariable(value = "isbn") String isbn){
        Book book = bookService.bookByIsbn(isbn);
        return ResponseHandler.generateResponse(HttpStatus.OK, "data", book);
    }

    @PostMapping("/book")
    public ResponseEntity<?> addBook(@RequestBody @Valid BookAddRequest book){
        Book result = bookService.addBook(book);
        producerService.sendBookId(BOOK_TOPIC, result.getId().toString());
        return ResponseHandler.generateResponse(HttpStatus.OK, "data", result);
    }

    @PatchMapping("/book/{id}")
    public ResponseEntity<?> editBook(@RequestBody @Valid BookEditRequest book, @PathVariable(value = "id") Long id){
        Book result = bookService.editBook(id, book);
        return ResponseHandler.generateResponse(HttpStatus.OK, "data", result);
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable(value = "id") Long id) {

        int deletedCount = bookService.deleteBookById(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, "deleted", deletedCount);
    }
}
