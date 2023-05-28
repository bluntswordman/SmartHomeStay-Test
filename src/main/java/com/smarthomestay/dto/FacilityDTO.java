package com.smarthomestay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FacilityDTO {
  private String facilityName;
  private int facilityPrice;
  private long roomId;
}
