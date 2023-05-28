package com.smarthomestay.controller;

import com.smarthomestay.dto.AuthResponse;
import com.smarthomestay.dto.LoginRequest;
import com.smarthomestay.dto.RegisterRequest;
import com.smarthomestay.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
    var user = authService.register(request);

    if (user.getToken() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(user);
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(user);
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
    var user = authService.login(request);

    if (user.getToken() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(user);
    }

    return ResponseEntity.status(HttpStatus.OK).body(user);
  }
}
