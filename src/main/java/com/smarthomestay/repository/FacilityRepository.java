package com.smarthomestay.repository;

import com.smarthomestay.entity.Facility;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacilityRepository extends CrudRepository<Facility, Long> {
  @Transactional
  @Modifying
  @Query(value = "INSERT INTO facilities (facility_name, facility_price, room_id) VALUES (:facilityName, :facilityPrice, :roomId)", nativeQuery = true)
  void create(@Param("facilityName") String facilityName, @Param("facilityPrice") int facilityPrice, @Param("roomId") Long roomId);

  @Transactional
  @Modifying
  @Query(value = "UPDATE facilities SET facility_name = :facilityName, facility_price = :facilityPrice, room_id = :roomId WHERE facility_id = :id", nativeQuery = true)
  void update(@Param("id") Long id, @Param("facilityName") String facilityName, @Param("facilityPrice") int facilityPrice, @Param("roomId") Long roomId);

  @Transactional
  @Modifying
  @Query(value = "SELECT * FROM facilities WHERE room_id = :roomId", nativeQuery = true)
  List<Facility> findAllByRoomId(@Param("roomId") Long roomId);
}
