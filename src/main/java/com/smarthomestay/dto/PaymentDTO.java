package com.smarthomestay.dto;

import com.smarthomestay.constant.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
  private Integer balance;
  private PaymentStatus statusPayment;
}
