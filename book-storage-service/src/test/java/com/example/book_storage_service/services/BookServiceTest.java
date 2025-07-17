package com.example.book_storage_service.services;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.book_storage_service.dto.BookAddRequest;
import com.example.book_storage_service.dto.BookEditRequest;
import com.example.book_storage_service.models.Book;
import com.example.book_storage_service.repo.BookRepository;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void allBooks_ReturnsBookList() throws Exception {
        List<Book> books = List.of(new Book(
            1L,
            "978-3-16-148410-0",
            "title1",
            "genre1",
            "description1",
            "author1"
        ),
        new Book(
            2L,
            "978-3-16-148869-0",
            "title2",
            "genre2",
            "description2",
            "author2"
        ));
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.allBooks();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("title1", result.get(0).getTitle());
        assertEquals("title2", result.get(1).getTitle()); 
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void bookByIsbn_BookExists_ReturnsBook() throws Exception {
        Book book = new Book(
            1L,
            "978-3-16-148410-0",
            "title1",
            "genre1",
            "description1",
            "author1"
        );
        when(bookRepository.findByIsbn(any(String.class))).thenReturn(Optional.of(book));

        Book result = bookService.bookByIsbn("978-3-16-148410-0");

        assertNotNull(result);
        assertEquals("title1", result.getTitle());
        assertEquals("978-3-16-148410-0", result.getIsbn());

        verify(bookRepository, times(1)).findByIsbn(any(String.class));
    }

    @Test
    void bookById_BookExists_ReturnsBook() throws Exception {
        Book book = new Book(
            1L,
            "978-3-16-148410-0",
            "title1",
            "genre1",
            "description1",
            "author1"
        );
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));

        Book result = bookService.bookById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("title1", result.getTitle());
        assertEquals("978-3-16-148410-0", result.getIsbn());
        verify(bookRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void addBook_ReturnsBook() throws Exception {
        BookAddRequest request = new BookAddRequest(
            "978-3-16-148410-0",
            "title1",
            "genre1",
            "description1",
            "author1"
        );
        Book book = new Book(
            1L,
            "978-3-16-148410-0",
            "title1",
            "genre1",
            "description1",
            "author1"
        );
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookRepository.existsByIsbn(any(String.class))).thenReturn(false);

        Book result = bookService.addBook(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("title1", result.getTitle());
        assertEquals("978-3-16-148410-0", result.getIsbn());
        verify(bookRepository, times(1)).save(any(Book.class));
        verify(bookRepository, times(1)).existsByIsbn(any(String.class));
    }

    @Test
    void editBook_BookExists_ReturnsBook() throws Exception {
        BookEditRequest request = new BookEditRequest(
            "978-3-16-148410-0",
            "newTitle1",
            "genre1",
            "newDescription1",
            "author1"
        );
        request.setAuthor(null);
        request.setIsbn(null);
        Book book = new Book(
            1L,
            "978-3-16-148410-0",
            "title1",
            "genre1",
            "description1",
            "author1"
        );
        Book newBook = new Book(
            1L,
            "978-3-16-148410-0",
            "newTitle1",
            "genre1",
            "newDescription1",
            "author1"
        );
        when(bookRepository.save(any(Book.class))).thenReturn(newBook);
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));

        Book result = bookService.editBook(1L, request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("newTitle1", result.getTitle());
        assertEquals("978-3-16-148410-0", result.getIsbn());
        assertEquals("author1", result.getAuthor());
        verify(bookRepository, times(1)).save(any(Book.class));
        verify(bookRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void deleteBookById_BookExists_ReturnsOne() throws Exception {
        Book book = new Book(
            1L,
            "978-3-16-148410-0",
            "title1",
            "genre1",
            "description1",
            "author1"
        );
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));

        int result = bookService.deleteBookById(1L);

        assertNotNull(result);
        assertEquals(1, result);
        verify(bookRepository, times(1)).findById(any(Long.class));
    }

}