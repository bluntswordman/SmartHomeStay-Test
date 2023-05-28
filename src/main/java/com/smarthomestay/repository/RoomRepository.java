package com.smarthomestay.repository;

import com.smarthomestay.constant.RoomType;
import com.smarthomestay.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Page<Room> findAllByType(RoomType type, Pageable pageable);

    Room findByUserId(Long userId);
}
