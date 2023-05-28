package com.smarthomestay.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smarthomestay.constant.RoomType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "rooms")
public class Room {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "room_id")
  private long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private RoomType type;

  @Column(name = "price")
  private int price;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "room_id", referencedColumnName = "room_id")
  private List<Facility> facilities;

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  @JsonIgnore
  private User user;

//  @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
//  private List<Bill> bills;
}
