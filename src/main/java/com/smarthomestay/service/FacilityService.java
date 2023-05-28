package com.smarthomestay.service;

import com.smarthomestay.dto.FacilityDTO;
import com.smarthomestay.entity.Facility;
import com.smarthomestay.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacilityService {
  private final FacilityRepository facilityRepository;
  private final ModelMapper modelMapper;

  public Facility save(FacilityDTO facility) {
    facilityRepository.create(facility.getFacilityName(), facility.getFacilityPrice(), facility.getRoomId());
    return modelMapper.map(facility, Facility.class);
  }

  public Facility update(long id, FacilityDTO facility) {
    facilityRepository.update(id, facility.getFacilityName(), facility.getFacilityPrice(), facility.getRoomId());
    return modelMapper.map(facility, Facility.class);
  }

  public Facility getFacilityById(long id) {
    return facilityRepository.findById(id).orElseThrow();
  }

  public List<Facility> getAllFacilities() {
    return (List<Facility>) facilityRepository.findAll();
  }

  public void delete(long id) {
    facilityRepository.deleteById(id);
  }
}
