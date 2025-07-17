package com.example.book_tracker_service.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.book_tracker_service.models.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByName(String username);
    boolean existsByName(String username);
}
