package com.example.book_storage_service.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.book_storage_service.exception.CustomHttpException;
import com.example.book_storage_service.models.User;
import com.example.book_storage_service.repo.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public User userByName(String name){
        Optional<User> user = userRepository.findByName(name);
        if (user.isPresent()) return user.get();
        else throw new UsernameNotFoundException(name + " not found");
    }

    public User userById(Long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) return user.get();
        else throw new CustomHttpException("User with id " + id + " not found", HttpStatus.NOT_FOUND);
    }

    public List<User> allUsers(){
        return userRepository.findAll();
    }

    public void addUser(User user) {
        if (userRepository.existsByName(user.getUsername())) {
            throw new CustomHttpException("User already exists", HttpStatus.CONFLICT);
        }
        userRepository.save(user);
    }

    public void deleteUserById(Long id){
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()){
            userRepository.deleteById(id);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByName(username);
        if (user.isPresent()) return user.get();
        else   throw new UsernameNotFoundException(username + " not found");
    }

}
