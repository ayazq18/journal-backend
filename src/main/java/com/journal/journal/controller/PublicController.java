package com.journal.journal.controller;


import com.journal.journal.entity.User;
import com.journal.journal.service.UserDetailsServiceImpl;
import com.journal.journal.service.UserService;
import com.journal.journal.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

   @GetMapping("/healthCheck")
   public String healthCheck(){
        return "Ok";
    }

    @Autowired
    private UserService UserService;

    @PostMapping("/signup")
    public void signup(@RequestBody User user){
        UserService.saveNewUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        try{
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
        String jwt = jwtUtil.generateToken(userDetails.getUsername());
        return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Incorrect UserName or Password", HttpStatus.BAD_REQUEST);
        }
    }
}
