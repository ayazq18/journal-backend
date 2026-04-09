package com.journal.journal.service;

import com.journal.journal.entity.User;
import com.journal.journal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository UserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = UserRepository.findByUserName(username);
        if(user != null){
            return org.springframework.security.core.userdetails.User.builder()
                     .username(user.getUserName())
                     .password(user.getPassword())
                     .roles(user.getRoles().toArray(new String[0]))
                     .build();
        }
        throw new UsernameNotFoundException("User not found");
    }
}
