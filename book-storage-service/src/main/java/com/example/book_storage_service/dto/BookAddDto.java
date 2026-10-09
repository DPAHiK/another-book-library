package com.example.book_storage_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;



public class BookAddDto {

    @Size(min = 17, max = 17, message = "ISBN должен состоять из 17 символов (13 цифр и 4 дефиса)")
    @NotBlank(message = "ISBN не может быть пустыми")
    private String isbn;

    @Size(min = 1, max = 255, message = "Название книги должно содержать от 1 до 255 символов")
    @NotBlank(message = "Название книги не может быть пустыми")
    private String title;

    @Size(min = 1, max = 255, message = "Жанр книги должен содержать от 1 до 255 символов")
    @NotBlank(message = "Жанр книги не может быть пустыми")
    private String genre;

    @Size(min = 0, max = 255, message = "Описание книги должно содержать от 1 до 255 символов")
    private String description;

    @Size(min = 1, max = 255, message = "Имя автора должно содержать от 1 до 255 символов")
    @NotBlank(message = "Имя автора не может быть пустыми")
    private String author;

    public BookAddDto(){}

    public BookAddDto(String isbn, String title, String genre, String description, String author) {
        this.isbn = isbn;
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.author = author;
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
}
