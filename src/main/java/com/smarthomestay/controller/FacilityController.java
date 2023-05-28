package com.smarthomestay.controller;

import com.smarthomestay.dto.FacilityDTO;
import com.smarthomestay.entity.Facility;
import com.smarthomestay.service.FacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/facility")
@RequiredArgsConstructor
public class FacilityController {
  private final FacilityService facilityService;

  @GetMapping
  public ResponseEntity<List<Facility>> getAllFacilities() {
    return ResponseEntity.status(HttpStatus.OK).body(facilityService.getAllFacilities());
  }

  @PostMapping
  public ResponseEntity<Facility> createFacility(@RequestBody FacilityDTO facility, Errors errors) {
    if (errors.hasErrors()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    return ResponseEntity.status(HttpStatus.CREATED).body(facilityService.save(facility));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Facility> updateFacility(@PathVariable long id, @RequestBody FacilityDTO facility, Errors errors) {
    if (errors.hasErrors()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    if (facilityService.getFacilityById(id) == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    return ResponseEntity.status(HttpStatus.OK).body(facilityService.update(id, facility));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Facility> deleteFacility(@PathVariable long id) {
    if (facilityService.getFacilityById(id) == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    facilityService.delete(id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
