package com.smarthomestay.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "facilities")
public class Facility {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "facility_id")
  @JsonIgnore
  private long id;

  @Column(name = "facility_name")
  private String facilityName;

  @Column(name = "facility_price")
  private int facilityPrice;
}
