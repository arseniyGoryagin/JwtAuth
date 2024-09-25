package com.arseniy.user.domain.dto;


import com.arseniy.user.domain.model.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserResponse {

    private final User user;

}
