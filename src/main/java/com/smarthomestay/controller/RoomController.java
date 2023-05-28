package com.smarthomestay.controller;

import com.smarthomestay.constant.RoomType;
import com.smarthomestay.dto.RoomDTO;
import com.smarthomestay.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/room")
@RequiredArgsConstructor
public class RoomController {
  private final RoomService roomService;

  @GetMapping
  public ResponseEntity<List<RoomDTO>> getAllRooms() {
    var rooms = roomService.getAllRooms();
    if (rooms.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body(rooms);
    }
    return ResponseEntity.status(HttpStatus.OK).body(rooms);
  }

  @GetMapping("/search")
  public ResponseEntity<List<RoomDTO>> searchRooms(
      @RequestParam(value = "type", required = false, defaultValue = "") RoomType type,
      @RequestParam(value = "page", required = false, defaultValue = "0") int page,
      @RequestParam(value = "size", required = false, defaultValue = "5") int size
  ) {
    var rooms = roomService.searchRooms(type, page, size).toList();
    if (rooms.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body(rooms);
    }
    return ResponseEntity.status(HttpStatus.OK).body(rooms);
  }

  @PostMapping
  public ResponseEntity<RoomDTO> createRoom(@RequestBody @Validated RoomDTO roomDTO, Errors errors) {
    if (errors.hasErrors()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    var room = roomService.createRoom(roomDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(room);
  }

  @PutMapping("/{id}")
  public ResponseEntity<RoomDTO> updateRoom(@PathVariable long id, @RequestBody @Validated RoomDTO roomDTO) {
    if (roomService.getRoomById(id).isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.status(HttpStatus.OK).body(roomService.updateRoom(id, roomDTO));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteRoom(@PathVariable long id) {
    if (roomService.getRoomById(id).isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    roomService.deleteRoom(id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
