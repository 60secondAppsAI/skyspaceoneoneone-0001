package com.skyspaceoneoneone.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.skyspaceoneoneone.domain.Flight;
import com.skyspaceoneoneone.dto.FlightDTO;
import com.skyspaceoneoneone.dto.FlightSearchDTO;
import com.skyspaceoneoneone.dto.FlightPageDTO;
import com.skyspaceoneoneone.dto.FlightConvertCriteriaDTO;
import com.skyspaceoneoneone.service.GenericService;
import com.skyspaceoneoneone.dto.common.RequestDTO;
import com.skyspaceoneoneone.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface FlightService extends GenericService<Flight, Integer> {

	List<Flight> findAll();

	ResultDTO addFlight(FlightDTO flightDTO, RequestDTO requestDTO);

	ResultDTO updateFlight(FlightDTO flightDTO, RequestDTO requestDTO);

    Page<Flight> getAllFlights(Pageable pageable);

    Page<Flight> getAllFlights(Specification<Flight> spec, Pageable pageable);

	ResponseEntity<FlightPageDTO> getFlights(FlightSearchDTO flightSearchDTO);
	
	List<FlightDTO> convertFlightsToFlightDTOs(List<Flight> flights, FlightConvertCriteriaDTO convertCriteria);

	FlightDTO getFlightDTOById(Integer flightId);







}





