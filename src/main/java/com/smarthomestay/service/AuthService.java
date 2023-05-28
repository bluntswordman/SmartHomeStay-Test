package com.smarthomestay.service;

import com.smarthomestay.constant.Role;
import com.smarthomestay.dto.AuthResponse;
import com.smarthomestay.dto.LoginRequest;
import com.smarthomestay.dto.RegisterRequest;
import com.smarthomestay.entity.User;
import com.smarthomestay.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final ModelMapper modelMapper;


  public AuthResponse register(RegisterRequest request) {
    var user = modelMapper.map(request, User.class);

    if (userRepository.findUserByEmail(request.getEmail()).isPresent()) {
      return AuthResponse.builder()
              .message("Email already exists")
              .build();
    }

    user.setPassword(passwordEncoder.encode(request.getPassword()));
    userRepository.save(user);

    var token = jwtService.generateToken(user);
    return AuthResponse.builder()
            .token(token)
            .message("Register successfully")
            .build();
  }

  public AuthResponse login(LoginRequest request) {
    if (userRepository.findUserByEmail(request.getEmail()).isEmpty()) {
      return AuthResponse.builder()
              .message("Wrong email")
              .build();
    }

    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );

    var token = jwtService.generateToken(userRepository.findUserByEmail(request.getEmail()).orElseThrow());
    if (token == null) {
      return AuthResponse.builder()
              .message("Wrong password")
              .build();
    }

    return AuthResponse.builder()
            .token(token)
            .message("Login successfully")
            .build();
  }

  public User getCurrentUser(HttpServletRequest request) {
    String token = extractTokenFromRequest(request);

    if (token != null && jwtService.isTokenValid(token)) {
      String email = jwtService.extractUsername(token);
      return userRepository.findUserByEmail(email).orElseThrow();
    }

    return null;
  }

  private String extractTokenFromRequest(HttpServletRequest request) {
    String authorizationHeader = request.getHeader("Authorization");

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      return authorizationHeader.substring(7);
    }

    return null;
  }
}
