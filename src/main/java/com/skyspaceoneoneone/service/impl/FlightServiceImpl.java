package com.skyspaceoneoneone.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



import com.skyspaceoneoneone.dao.GenericDAO;
import com.skyspaceoneoneone.service.GenericService;
import com.skyspaceoneoneone.service.impl.GenericServiceImpl;
import com.skyspaceoneoneone.dao.FlightDAO;
import com.skyspaceoneoneone.domain.Flight;
import com.skyspaceoneoneone.dto.FlightDTO;
import com.skyspaceoneoneone.dto.FlightSearchDTO;
import com.skyspaceoneoneone.dto.FlightPageDTO;
import com.skyspaceoneoneone.dto.FlightConvertCriteriaDTO;
import com.skyspaceoneoneone.dto.common.RequestDTO;
import com.skyspaceoneoneone.dto.common.ResultDTO;
import com.skyspaceoneoneone.service.FlightService;
import com.skyspaceoneoneone.util.ControllerUtils;





@Service
public class FlightServiceImpl extends GenericServiceImpl<Flight, Integer> implements FlightService {

    private final static Logger logger = LoggerFactory.getLogger(FlightServiceImpl.class);

	@Autowired
	FlightDAO flightDao;

	


	@Override
	public GenericDAO<Flight, Integer> getDAO() {
		return (GenericDAO<Flight, Integer>) flightDao;
	}
	
	public List<Flight> findAll () {
		List<Flight> flights = flightDao.findAll();
		
		return flights;	
		
	}

	public ResultDTO addFlight(FlightDTO flightDTO, RequestDTO requestDTO) {

		Flight flight = new Flight();

		flight.setFlightId(flightDTO.getFlightId());


		flight.setFlightNumber(flightDTO.getFlightNumber());


		flight.setDepartureTime(flightDTO.getDepartureTime());


		flight.setArrivalTime(flightDTO.getArrivalTime());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		flight = flightDao.save(flight);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Flight> getAllFlights(Pageable pageable) {
		return flightDao.findAll(pageable);
	}

	public Page<Flight> getAllFlights(Specification<Flight> spec, Pageable pageable) {
		return flightDao.findAll(spec, pageable);
	}

	public ResponseEntity<FlightPageDTO> getFlights(FlightSearchDTO flightSearchDTO) {
	
			Integer flightId = flightSearchDTO.getFlightId(); 
 			String flightNumber = flightSearchDTO.getFlightNumber(); 
     			String sortBy = flightSearchDTO.getSortBy();
			String sortOrder = flightSearchDTO.getSortOrder();
			String searchQuery = flightSearchDTO.getSearchQuery();
			Integer page = flightSearchDTO.getPage();
			Integer size = flightSearchDTO.getSize();

	        Specification<Flight> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, flightId, "flightId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, flightNumber, "flightNumber"); 
			
 			
 			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("flightNumber")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Flight> flights = this.getAllFlights(spec, pageable);
		
		//System.out.println(String.valueOf(flights.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(flights.getTotalPages()));
		
		List<Flight> flightsList = flights.getContent();
		
		FlightConvertCriteriaDTO convertCriteria = new FlightConvertCriteriaDTO();
		List<FlightDTO> flightDTOs = this.convertFlightsToFlightDTOs(flightsList,convertCriteria);
		
		FlightPageDTO flightPageDTO = new FlightPageDTO();
		flightPageDTO.setFlights(flightDTOs);
		flightPageDTO.setTotalElements(flights.getTotalElements());
		return ResponseEntity.ok(flightPageDTO);
	}

	public List<FlightDTO> convertFlightsToFlightDTOs(List<Flight> flights, FlightConvertCriteriaDTO convertCriteria) {
		
		List<FlightDTO> flightDTOs = new ArrayList<FlightDTO>();
		
		for (Flight flight : flights) {
			flightDTOs.add(convertFlightToFlightDTO(flight,convertCriteria));
		}
		
		return flightDTOs;

	}
	
	public FlightDTO convertFlightToFlightDTO(Flight flight, FlightConvertCriteriaDTO convertCriteria) {
		
		FlightDTO flightDTO = new FlightDTO();
		
		flightDTO.setFlightId(flight.getFlightId());

	
		flightDTO.setFlightNumber(flight.getFlightNumber());

	
		flightDTO.setDepartureTime(flight.getDepartureTime());

	
		flightDTO.setArrivalTime(flight.getArrivalTime());

	

		
		return flightDTO;
	}

	public ResultDTO updateFlight(FlightDTO flightDTO, RequestDTO requestDTO) {
		
		Flight flight = flightDao.getById(flightDTO.getFlightId());

		flight.setFlightId(ControllerUtils.setValue(flight.getFlightId(), flightDTO.getFlightId()));

		flight.setFlightNumber(ControllerUtils.setValue(flight.getFlightNumber(), flightDTO.getFlightNumber()));

		flight.setDepartureTime(ControllerUtils.setValue(flight.getDepartureTime(), flightDTO.getDepartureTime()));

		flight.setArrivalTime(ControllerUtils.setValue(flight.getArrivalTime(), flightDTO.getArrivalTime()));



        flight = flightDao.save(flight);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public FlightDTO getFlightDTOById(Integer flightId) {
	
		Flight flight = flightDao.getById(flightId);
			
		
		FlightConvertCriteriaDTO convertCriteria = new FlightConvertCriteriaDTO();
		return(this.convertFlightToFlightDTO(flight,convertCriteria));
	}







}
