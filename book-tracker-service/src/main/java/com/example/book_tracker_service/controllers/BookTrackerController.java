package com.example.book_tracker_service.controllers;

import com.example.book_tracker_service.models.BookTracker;
import com.example.book_tracker_service.dto.ResponseHandler;
import com.example.book_tracker_service.dto.BookTrackerUpdateDto;
import com.example.book_tracker_service.dto.BookTrackerAddDto;
import com.example.book_tracker_service.services.BookTrackerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import jakarta.validation.Valid;


@RestController
public class BookTrackerController {

    private static final Logger logger = LoggerFactory.getLogger(BookTrackerService.class);

    final private BookTrackerService bookTrackerService;

    public BookTrackerController(BookTrackerService bookTrackerService) {
        this.bookTrackerService = bookTrackerService;
    }

    @GetMapping("/book/free")
    public ResponseEntity<?> getFreeBooks(){
        return ResponseHandler.generateResponse(HttpStatus.OK, "data", bookTrackerService.findFreeBooks());
    }

    @PostMapping("/book/tracker")
    public ResponseEntity<?> addBookTracker(@RequestBody @Valid BookTrackerAddDto bookTrackerData){
        BookTracker result = bookTrackerService.addBookTracker(bookTrackerData);

        return ResponseHandler.generateResponse(HttpStatus.OK, "added", result);
    }

    @DeleteMapping("/book/tracker/{id}")
    public ResponseEntity<?> deleteBookTracker(@PathVariable(value = "id") Long id){
        boolean result = bookTrackerService.deleteBookTrackerById(id);

        return ResponseHandler.generateResponse(HttpStatus.OK, "deleted", result);

    }

    @PatchMapping("/book/tracker/{id}")
    public ResponseEntity<?> editBookTracker(@RequestBody BookTrackerUpdateDto bookTrackerData, @PathVariable(value = "id") Long id){
        Optional<BookTracker> result = bookTrackerService.editBookTracker(id, bookTrackerData);
        return result.isPresent() ? 
                ResponseHandler.generateResponse( HttpStatus.OK, "edited", result) : 
                ResponseHandler.generateResponse( HttpStatus.NOT_FOUND, "message", "Book tracker with id " + id + " not found");
    }
}
