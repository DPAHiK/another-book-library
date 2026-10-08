package com.example.book_tracker_service.services;

import com.example.book_tracker_service.models.BookTracker;
import com.example.book_tracker_service.dto.BookTrackerUpdateDto;
import com.example.book_tracker_service.dto.BookTrackerAddDto;
import com.example.book_tracker_service.mapper.BookTrackerMapper;
import com.example.book_tracker_service.repo.BookTrackerRepository;
import com.example.book_tracker_service.exception.CustomHttpException;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookTrackerService {

    private static final Logger logger = LoggerFactory.getLogger(BookTrackerService.class);

    private final BookTrackerRepository bookTrackerRepository;

    private final BookTrackerMapper bookTrackerMapper;

    public BookTrackerService(BookTrackerRepository bookTrackerRepository,  BookTrackerMapper bookTrackerMapper){
        this.bookTrackerRepository = bookTrackerRepository;
        this.bookTrackerMapper = bookTrackerMapper;
    }

    public Optional<BookTracker> findById(Long id){
        return bookTrackerRepository.findById(id);
    }

    public List<BookTracker> findFreeBooks(){
        return bookTrackerRepository.findByIsFree(true);
    }

    public BookTracker addBookTracker(BookTrackerAddDto bookTrackerData){
        BookTracker bookTracker = new BookTracker(
                                bookTrackerData.getBookId(),
                                bookTrackerData.isFree(),
                                bookTrackerData.getTakeDate(),
                                bookTrackerData.getReturnDate()
                                );
        return bookTrackerRepository.save(bookTracker);
    }

    public BookTracker editBookTracker(Long id, BookTrackerUpdateDto bookTrackerData){
        Optional<BookTracker> oldBookTracker = bookTrackerRepository.findById(id);
        if (!oldBookTracker.isPresent()) throw new CustomHttpException("Book tracker with id " + id + " not found", HttpStatus.NOT_FOUND);
        
        BookTracker newBookTracker = oldBookTracker.get();
        bookTrackerMapper.updateBookTracker(bookTrackerData, newBookTracker);
        return bookTrackerRepository.save(newBookTracker);
    }

    public int deleteBookTrackerById(Long id){
        Optional<BookTracker> book = bookTrackerRepository.findById(id);

        if(book.isPresent()){
            bookTrackerRepository.deleteById(id);
            return 1;
        }

        return 0;
    }

    public int deleteBookTrackerByBookId(Long bookId){
        Optional<BookTracker> book = bookTrackerRepository.findByBookId(bookId);

        if(book.isPresent()){
            bookTrackerRepository.deleteById(book.get().getId());
            return 1;
        }

        return 0;
    }
}
