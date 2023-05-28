package com.smarthomestay.controller;

import com.smarthomestay.constant.PaymentStatus;
import com.smarthomestay.dto.PaymentDTO;
import com.smarthomestay.dto.TransactionDTO;
import com.smarthomestay.service.AuthService;
import com.smarthomestay.service.RoomService;
import com.smarthomestay.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {
  private final TransactionService transactionService;
  private final AuthService authService;
  private final RoomService roomService;

  @PostMapping("/add-room-to-user")
  public ResponseEntity<?> addRoomToUser(@RequestBody TransactionDTO transaction, HttpServletRequest request, Errors errors) {
    if (errors.hasErrors()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());
    }

    if (roomService.getRoomById(transaction.getRoomId()).isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Room not found");
    }

    long userId = authService.getCurrentUser(request).getId();
    if (userId == 0) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    transactionService.addRoomToUser(userId, transaction.getRoomId());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/calculate-price")
  public ResponseEntity<?> calculatePrice(HttpServletRequest request) {
    long userId = authService.getCurrentUser(request).getId();
    if (userId == 0) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    double price = transactionService.calculatePrice(userId);
    Map<String, Double> result = Map.of("Total Price", price);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @GetMapping("/check-out")
  public ResponseEntity<?> checkOut(HttpServletRequest request) {
    long userId = authService.getCurrentUser(request).getId();
    if (userId == 0) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    Object result = transactionService.checkOut(userId);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }


  @PostMapping("/receive-payment")
  public ResponseEntity<?> receivePayment(@RequestBody PaymentDTO payment, HttpServletRequest request, Errors errors) {
    long userId = authService.getCurrentUser(request).getId();
    if (userId == 0) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    Object result = transactionService.receivePayment(userId, payment);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
