package org.viagratiae.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.viagratiae.springbootdeveloper.domain.User;
import org.viagratiae.springbootdeveloper.dto.AddUserRequest;
import org.viagratiae.springbootdeveloper.repository.UserRepository;

// 신규 가입
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest request) {
        return userRepository.save(User.builder()
                .email(request.getEmail())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .build()).getId();
    }
}
