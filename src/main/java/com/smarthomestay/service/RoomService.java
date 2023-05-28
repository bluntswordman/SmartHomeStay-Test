package com.smarthomestay.service;

import com.smarthomestay.constant.RoomType;
import com.smarthomestay.dto.RoomDTO;
import com.smarthomestay.entity.Room;
import com.smarthomestay.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {
  private final RoomRepository roomRepository;
  private final ModelMapper modelMapper;

  public Optional<RoomDTO> getRoomById(long id) {
    return roomRepository.findById(id).map(room -> modelMapper.map(room, RoomDTO.class));
  }

  public List<RoomDTO> getAllRooms() {
    return roomRepository.findAll().stream()
        .map(room -> modelMapper.map(room, RoomDTO.class))
        .toList();
  }

  public Page<RoomDTO> searchRooms(RoomType type, int page, int size) {
    Pageable pageable = Pageable.ofSize(size).withPage(page);
    return roomRepository.findAllByType(type, pageable)
        .map(room -> modelMapper.map(room, RoomDTO.class));
  }

  public RoomDTO createRoom(RoomDTO roomDTO) {
    var room = modelMapper.map(roomDTO, Room.class);
    roomRepository.save(room);
    return modelMapper.map(room, RoomDTO.class);
  }

  public RoomDTO updateRoom(long id, RoomDTO roomDTO) {
    var room = roomRepository.findById(id).orElseThrow();
    room.setPrice(roomDTO.getPrice());
    room.setType(roomDTO.getType());
    roomRepository.save(room);
    return modelMapper.map(room, RoomDTO.class);
  }

  public void deleteRoom(long id) {
    roomRepository.deleteById(id);
  }
}
