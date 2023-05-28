package com.smarthomestay.dto;

import com.smarthomestay.constant.RoomType;
import com.smarthomestay.entity.Facility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
  private RoomType type;
  private int price;
  private List<Facility> facilities;
}
