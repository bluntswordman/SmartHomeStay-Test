package com.smarthomestay.service;

import com.smarthomestay.constant.PaymentStatus;
import com.smarthomestay.dto.PaymentDTO;
import com.smarthomestay.entity.Bill;
import com.smarthomestay.entity.Room;
import com.smarthomestay.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TransactionService {
  private final TransactionRepository transactionRepository;
  private final UserRepository userRepository;
  private final RoomRepository roomRepository;
  private final BillRepository billRepository;

  public void addRoomToUser(long userId, long roomId) {
    transactionRepository.addRoomToUser(userId, roomId);
    transactionRepository.checkIn(userId);
  }

  public double calculatePrice(long userId) {
    double result = 0;
    Room room = roomRepository.findByUserId(userId);

    for (var facility : room.getFacilities()) {
      result += facility.getFacilityPrice();
    }

    result += room.getPrice();
    billRepository.calculatePaymentTotal((int) result, new Date(), userId, "UNPAID");

    return result;
  }

  public Object receivePayment(long userId, PaymentDTO paymentDTO) {
    var checkBill = billRepository.findByUserId(userId);

    if (checkBill.getPaymentStatus() == PaymentStatus.PAID) {
      return "Bill has been paid";
    }

    if (checkBill.getPaymentTotal() > paymentDTO.getBalance()) {
      return "Balance is not enough";
    }

    if (checkBill.getPaymentTotal() < paymentDTO.getBalance()) {
      return "please pay the exact amount";
    }

    transactionRepository.receivePayment(userId, 0, "PAID");
    return "Receive payment success";
  }

  public Object checkOut(long userId) {
    var checkBill = billRepository.findByUserId(userId);

    if (checkBill.getPaymentStatus() == PaymentStatus.UNPAID) {
      return "Bill has not been paid";
    }

    transactionRepository.checkOut(userId);
    return "Check out success";
  }
}
