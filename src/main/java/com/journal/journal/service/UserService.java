package com.journal.journal.service;

import com.journal.journal.entity.JournalEntry;
import com.journal.journal.entity.User;
import com.journal.journal.repository.JournalEntryRepository;
import com.journal.journal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository UserRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Use this only for INITIAL sign-up
    public void saveNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new ArrayList<>(List.of("USER")));
        UserRepository.save(user);
    }

    // Use this for updating existing users (like adding journals)
    public void saveUser(User user){
        UserRepository.save(user);
    }

    public void saveAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new ArrayList<>(List.of("USER", "ADMIN")));
        UserRepository.save(user);
    }

    public List<User> getAll(){
       return UserRepository.findAll();
    }

    public Optional<User> getById(Long id){
        return UserRepository.findById(id);
    }

    public void deleteById(Long id){
        UserRepository.deleteById(id);
    }

    public User findByUserName(String user){
        return UserRepository.findByUserName(user);
    }
}
