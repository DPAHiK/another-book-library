package com.example.book_storage_service.mapper;

import com.example.book_storage_service.dto.BookUpdateDto;
import com.example.book_storage_service.models.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = "spring",
        config = PatchMapperConfig.class
)
public interface BookMapper {

    void updateBook(
            BookUpdateDto dto,
            @MappingTarget Book book
    );
}