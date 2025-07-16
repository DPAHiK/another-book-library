package com.example.book_storage_service.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String isbn;
    private String title;
    private String genre;
    private String description;
    private String author;

    public Book(){}

    public Book(Long id, String isbn, String title, String genre, String description, String author) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.author = author;
    }

    public Book(builder b) {
        this.isbn = b.isbn;
        this.title = b.title;
        this.genre = b.genre;
        this.description = b.description;
        this.author = b.author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public static class builder{

        private String isbn;
        private String title;
        private String genre;
        private String description;
        private String author;

        public builder(){}

        public builder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public builder title(String title) {
            this.title = title;
            return this;
        }

        public builder genre(String genre) {
            this.genre = genre;
            return this;
        }

        public builder author(String author) {
            this.author = author;
            return this;
        }

        public builder description(String description) {
            this.description = description;
            return this;
        }

        public Book build(){
            return new Book(this);
        }
    }
}
