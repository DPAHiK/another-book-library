package com.example.book_tracker_service.mapper;

import com.example.book_tracker_service.dto.BookTrackerUpdateDto;
import com.example.book_tracker_service.models.BookTracker;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = "spring",
        config = PatchMapperConfig.class
)
public interface BookTrackerMapper {

    void updateBookTracker(
            BookTrackerUpdateDto dto,
            @MappingTarget BookTracker bookTracker
    );
}