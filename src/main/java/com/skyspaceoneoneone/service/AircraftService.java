package com.skyspaceoneoneone.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.skyspaceoneoneone.domain.Aircraft;
import com.skyspaceoneoneone.dto.AircraftDTO;
import com.skyspaceoneoneone.dto.AircraftSearchDTO;
import com.skyspaceoneoneone.dto.AircraftPageDTO;
import com.skyspaceoneoneone.dto.AircraftConvertCriteriaDTO;
import com.skyspaceoneoneone.service.GenericService;
import com.skyspaceoneoneone.dto.common.RequestDTO;
import com.skyspaceoneoneone.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface AircraftService extends GenericService<Aircraft, Integer> {

	List<Aircraft> findAll();

	ResultDTO addAircraft(AircraftDTO aircraftDTO, RequestDTO requestDTO);

	ResultDTO updateAircraft(AircraftDTO aircraftDTO, RequestDTO requestDTO);

    Page<Aircraft> getAllAircrafts(Pageable pageable);

    Page<Aircraft> getAllAircrafts(Specification<Aircraft> spec, Pageable pageable);

	ResponseEntity<AircraftPageDTO> getAircrafts(AircraftSearchDTO aircraftSearchDTO);
	
	List<AircraftDTO> convertAircraftsToAircraftDTOs(List<Aircraft> aircrafts, AircraftConvertCriteriaDTO convertCriteria);

	AircraftDTO getAircraftDTOById(Integer aircraftId);







}





