package com.arseniy.auth.services;


import com.arseniy.auth.domain.exceptions.UserException;
import com.arseniy.user.domain.model.User;
import com.arseniy.user.repository.UserRepository;
import com.arseniy.jwt.domain.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorizationService {


    private final  UserRepository userRepository;
    private  final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public String login(String username, String password){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return jwtService.generateToken(username);
    }



    public String register(String username, @NotNull String password, String email) throws UserException {



        log.info("PASWORD + "+ password);
        log.info("username + "+ username);


        // Validate if user have is already registered

        Optional<User> user =  userRepository.findByEmail(email);

        if(user.isPresent()){
            throw new UserException("Email already in use");
        }

        user = userRepository.findByUsername(username);


        if(user.isPresent()){
            throw new UserException("username already in use");
        }


        // Add user to db

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRole(User.Role.USER);

        log.info(newUser.toString());

        userRepository.save(newUser);


        // Generate token

        return jwtService.generateToken(newUser.getUsername());



    }




}
