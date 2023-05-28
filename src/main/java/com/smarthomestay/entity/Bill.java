package com.smarthomestay.entity;

import com.smarthomestay.constant.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "bill")
public class Bill {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "bill_id")
  private long id;

  @Column(name = "payment_total")
  private int paymentTotal;

  @Enumerated(EnumType.STRING)
  private PaymentStatus paymentStatus = PaymentStatus.UNPAID;

  @Column(name = "payment_date")
  private Date paymentDate;
}
