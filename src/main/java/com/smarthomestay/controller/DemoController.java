package com.smarthomestay.controller;

import com.smarthomestay.entity.User;
import com.smarthomestay.repository.RoomRepository;
import com.smarthomestay.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class DemoController {
  @GetMapping("/employee")
  public String getEmployee() {
    return "Hello Employee";
  }

  @GetMapping("/user")
  public String getUser() {
    return "Hello User";
  }


  private final AuthService authService;

  @GetMapping("/test")
  public ResponseEntity<User> getCurrentUser(HttpServletRequest request) {
    User user = authService.getCurrentUser(request);

    if (user != null) {
      return ResponseEntity.ok(user);
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

}
