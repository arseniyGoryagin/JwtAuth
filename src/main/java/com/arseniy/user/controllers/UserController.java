package com.arseniy.user.controllers;


import com.arseniy.auth.domain.dto.ErrorResponse;
import com.arseniy.user.domain.model.User;
import com.arseniy.user.domain.dto.UserResponse;
import com.arseniy.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    @GetMapping()
    public ResponseEntity<UserResponse> getUser(@RequestParam("username") String username){
        User user = (User) userService.getByUsername(username);
        return new ResponseEntity<>(new UserResponse(user), HttpStatus.OK);
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UsernameNotFoundException e){
        ErrorResponse resp  = new ErrorResponse();
        resp.setMessage(e.getLocalizedMessage());
        return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUserException(Exception e){

        ErrorResponse error = new ErrorResponse();
        error.setMessage(e.getLocalizedMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
