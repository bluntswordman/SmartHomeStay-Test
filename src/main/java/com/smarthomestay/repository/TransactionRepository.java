package com.smarthomestay.repository;

import com.smarthomestay.constant.PaymentStatus;
import com.smarthomestay.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<User, Long> {
  @Transactional
  @Modifying
  @Query(value = "update rooms set user_id = :userId where room_id = :roomId", nativeQuery = true)
  void addRoomToUser(@Param("userId") Long userId, @Param("roomId") Long roomId);

  @Transactional
  @Modifying
  @Query(value = "update users set is_check_in = true where user_id = :userId", nativeQuery = true)
  void checkIn(@Param("userId") Long userId);

  @Transactional
  @Modifying
  @Query(value = "update bill set payment_total = :balance, payment_status = :statusPayment where user_id = :userId", nativeQuery = true)
  void receivePayment(@Param("userId") Long userId, @Param("balance") Integer balance, @Param("statusPayment") String statusPayment);

  @Transactional
  @Modifying
  @Query(value = "update users set is_check_in = false, is_check_out = true where user_id = :userId", nativeQuery = true)
  void checkOut(@Param("userId") Long userId);
}
