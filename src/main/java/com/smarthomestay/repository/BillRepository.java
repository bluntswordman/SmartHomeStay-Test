package com.smarthomestay.repository;

import com.smarthomestay.constant.PaymentStatus;
import com.smarthomestay.entity.Bill;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface BillRepository extends CrudRepository<Bill, Long> {
  @Transactional
  @Modifying
  @Query(value = "INSERT INTO bill (payment_total, payment_date) VALUES (:paymentTotal, :paymentDate)", nativeQuery = true)
  void create(@Param("paymentTotal") int paymentTotal, @Param("paymentDate") Date paymentDate);

  @Transactional
  @Modifying
  @Query(value = "INSERT INTO bill (payment_total, payment_date, user_id, payment_status) VALUES (:paymentTotal, :paymentDate, :userId, :statusPayment)", nativeQuery = true)
  void calculatePaymentTotal(@Param("paymentTotal") int paymentTotal, @Param("paymentDate") Date paymentDate, @Param("userId") Long userId, @Param("statusPayment") String statusPayment);


  @Transactional
  @Query(value = "SELECT * FROM bill WHERE user_id = :userId", nativeQuery = true)
  Bill findByUserId(@Param("userId") Long userId);
}
