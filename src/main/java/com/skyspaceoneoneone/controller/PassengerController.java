package com.skyspaceoneoneone.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;


import com.skyspaceoneoneone.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.skyspaceoneoneone.domain.Passenger;
import com.skyspaceoneoneone.dto.PassengerDTO;
import com.skyspaceoneoneone.dto.PassengerSearchDTO;
import com.skyspaceoneoneone.dto.PassengerPageDTO;
import com.skyspaceoneoneone.service.PassengerService;
import com.skyspaceoneoneone.dto.common.RequestDTO;
import com.skyspaceoneoneone.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/passenger")
@RestController
public class PassengerController {

	private final static Logger logger = LoggerFactory.getLogger(PassengerController.class);

	@Autowired
	PassengerService passengerService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Passenger> getAll() {

		List<Passenger> passengers = passengerService.findAll();
		
		return passengers;	
	}

	@GetMapping(value = "/{passengerId}")
	@ResponseBody
	public PassengerDTO getPassenger(@PathVariable Integer passengerId) {
		
		return (passengerService.getPassengerDTOById(passengerId));
	}

 	@RequestMapping(value = "/addPassenger", method = RequestMethod.POST)
	public ResponseEntity<?> addPassenger(@RequestBody PassengerDTO passengerDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = passengerService.addPassenger(passengerDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/passengers")
	public ResponseEntity<PassengerPageDTO> getPassengers(PassengerSearchDTO passengerSearchDTO) {
 
		return passengerService.getPassengers(passengerSearchDTO);
	}	

	@RequestMapping(value = "/updatePassenger", method = RequestMethod.POST)
	public ResponseEntity<?> updatePassenger(@RequestBody PassengerDTO passengerDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = passengerService.updatePassenger(passengerDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
