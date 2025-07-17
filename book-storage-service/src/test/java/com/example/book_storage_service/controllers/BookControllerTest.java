package com.example.book_storage_service.controllers;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.book_storage_service.dto.BookAddRequest;
import com.example.book_storage_service.dto.BookEditRequest;
import com.example.book_storage_service.models.Book;
import com.example.book_storage_service.services.BookService;
import com.example.book_storage_service.services.ProducerService;
import static com.example.book_storage_service.services.ProducerService.BOOK_TOPIC;

class BookControllerTest {

    @Mock
    private BookService bookService;

    @Mock
    private ProducerService producerService;

    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void getBooks_ValidRequest_ReturnsResponseEntity() throws Exception {
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

        when(bookService.allBooks()).thenReturn(books);
        mockMvc.perform(get("/book"))
                .andExpect(status().isOk())

                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("200"))

                .andExpect(jsonPath("$.data[0].id").value(1L))
                .andExpect(jsonPath("$.data[0].isbn").value("978-3-16-148410-0"))
                .andExpect(jsonPath("$.data[0].title").value("title1"))
                .andExpect(jsonPath("$.data[0].genre").value("genre1"))
                .andExpect(jsonPath("$.data[0].description").value("description1"))
                .andExpect(jsonPath("$.data[0].author").value("author1"))
                
                .andExpect(jsonPath("$.data[1].id").value(2L))
                .andExpect(jsonPath("$.data[1].isbn").value("978-3-16-148869-0"))
                .andExpect(jsonPath("$.data[1].title").value("title2"))
                .andExpect(jsonPath("$.data[1].genre").value("genre2"))
                .andExpect(jsonPath("$.data[1].description").value("description2"))
                .andExpect(jsonPath("$.data[1].author").value("author2"));

        verify(bookService, times(1)).allBooks();
    }

    @Test
    void bookByID_ValidRequest_ReturnsResponseEntity() throws Exception {
        Book book = new Book(
            1L,
            "978-3-16-148410-0",
            "title1",
            "genre1",
            "description1",
            "author1"
        );

        when(bookService.bookById(1L)).thenReturn(book);
        mockMvc.perform(get("/book/1"))
                .andExpect(status().isOk())

                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("200"))

                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.isbn").value("978-3-16-148410-0"))
                .andExpect(jsonPath("$.data.title").value("title1"))
                .andExpect(jsonPath("$.data.genre").value("genre1"))
                .andExpect(jsonPath("$.data.description").value("description1"))
                .andExpect(jsonPath("$.data.author").value("author1"));

        verify(bookService, times(1)).bookById(1L);
    }

    @Test
    void bookByIsbn_ValidRequest_ReturnsResponseEntity() throws Exception {
        Book book = new Book(
            1L,
            "978-3-16-148410-0",
            "title1",
            "genre1",
            "description1",
            "author1"
        );

        when(bookService.bookByIsbn("978-3-16-148410-0")).thenReturn(book);
        mockMvc.perform(get("/book/isbn/978-3-16-148410-0"))
                .andExpect(status().isOk())

                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("200"))

                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.isbn").value("978-3-16-148410-0"))
                .andExpect(jsonPath("$.data.title").value("title1"))
                .andExpect(jsonPath("$.data.genre").value("genre1"))
                .andExpect(jsonPath("$.data.description").value("description1"))
                .andExpect(jsonPath("$.data.author").value("author1"));

        verify(bookService, times(1)).bookByIsbn("978-3-16-148410-0");
    }

    @Test
    void addBook_ValidRequest_ReturnsResponseEntity() throws Exception {
        Book returnedBook = new Book(
            1L,
            "978-3-16-148410-0",
            "title1",
            "genre1",
            "description1",
            "author1"
        );

        when(bookService.addBook(any(BookAddRequest.class))).thenReturn(returnedBook);

        mockMvc.perform(post("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n\"isbn\":\"978-3-16-148410-0\",\n\"title\":\"title1\",\n\"author\":\"author1\",\n\"genre\":\"genre1\",\n\"description\":\"description1\"\n}"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("200"))
                .andExpect(jsonPath("$.message").value("Book added (id: 1)"));

        verify(bookService, times(1)).addBook(any(BookAddRequest.class));
        verify(producerService, times(1)).sendBookId(BOOK_TOPIC, "1");
    }

    @Test
    void editBook_ValidRequest_ReturnsResponseEntity() throws Exception {
        Book returnedBook = new Book(
            1L,
            "978-3-16-148410-0",
            "newTitle1",
            "genre1",
            "newDescription1",
            "author1"
        );

        when(bookService.editBook(eq(1L), any(BookEditRequest.class))).thenReturn(returnedBook);

        mockMvc.perform(put("/book/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n\"isbn\":\"978-3-16-148410-0\",\n\"title\":\"newTitle1\",\n\"genre\":\"genre1\",\n\"description\":\"newDescription1\"\n}"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("200"))
                .andExpect(jsonPath("$.message").value("Book with id 1 edited"));

        verify(bookService, times(1)).editBook(eq(1L), any(BookEditRequest.class));
    }

    @Test
    void deleteBook_ValidRequest_ReturnsResponseEntity() throws Exception {

        when(bookService.deleteBookById(1L)).thenReturn(1);

        mockMvc.perform(delete("/book/1"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("200"))
                .andExpect(jsonPath("$.message").value("Books deleted: 1"));

        verify(bookService, times(1)).deleteBookById(1L);
    }
}