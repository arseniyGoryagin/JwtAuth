package com.arseniy.user.services;


import com.arseniy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

        private final UserRepository userRepository;

        public UserDetails getByUsername(String username) {
               return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("no such user"));
        }

        public UserDetailsService userDetailsService() {
                return this::getByUsername;
        }
}
