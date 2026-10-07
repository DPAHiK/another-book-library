package com.example.book_tracker_service.dto;

import jakarta.validation.constraints.NotBlank;

import java.sql.Date;


public class BookTrackerUpdateDto {

    @NotBlank(message = "id книги не может быть пустыми")
    private Long bookId;

    @NotBlank(message = "Укажите статус книги")
    private Boolean isFree;

    private Date returnDate;
    private Date takeDate;

    public BookTrackerUpdateDto(){
    }

    public BookTrackerAddDto(Long bookId, boolean isFree, Date takeDate, Date returnDate) {
        this.bookId = bookId;
        this.isFree = isFree;
        this.takeDate = takeDate;
        this.returnDate = returnDate;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public Date getTakeDate() {
        return takeDate;
    }

    public void setTakeDate(Date takeDate) {
        this.takeDate = takeDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

}
