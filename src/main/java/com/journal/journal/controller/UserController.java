package com.journal.journal.controller;

import com.journal.journal.entity.JournalEntry;
import com.journal.journal.entity.User;
import com.journal.journal.repository.UserRepository;
import com.journal.journal.service.JournalEntryService;
import com.journal.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
class UserController {

    @Autowired
    private UserService UserService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public void addNewUser(@RequestBody User user){
            UserService.saveNewUser(user);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDB = UserService.findByUserName(userName);
           userInDB.setUserName(user.getUserName());
           userInDB.setPassword(user.getPassword());
           UserService.saveNewUser(userInDB);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> greetings(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>("Hi " + authentication.getName(), HttpStatus.OK);
    }

}
