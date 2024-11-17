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

import com.skyspaceoneoneone.domain.Seat;
import com.skyspaceoneoneone.dto.SeatDTO;
import com.skyspaceoneoneone.dto.SeatSearchDTO;
import com.skyspaceoneoneone.dto.SeatPageDTO;
import com.skyspaceoneoneone.service.SeatService;
import com.skyspaceoneoneone.dto.common.RequestDTO;
import com.skyspaceoneoneone.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/seat")
@RestController
public class SeatController {

	private final static Logger logger = LoggerFactory.getLogger(SeatController.class);

	@Autowired
	SeatService seatService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Seat> getAll() {

		List<Seat> seats = seatService.findAll();
		
		return seats;	
	}

	@GetMapping(value = "/{seatId}")
	@ResponseBody
	public SeatDTO getSeat(@PathVariable Integer seatId) {
		
		return (seatService.getSeatDTOById(seatId));
	}

 	@RequestMapping(value = "/addSeat", method = RequestMethod.POST)
	public ResponseEntity<?> addSeat(@RequestBody SeatDTO seatDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = seatService.addSeat(seatDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/seats")
	public ResponseEntity<SeatPageDTO> getSeats(SeatSearchDTO seatSearchDTO) {
 
		return seatService.getSeats(seatSearchDTO);
	}	

	@RequestMapping(value = "/updateSeat", method = RequestMethod.POST)
	public ResponseEntity<?> updateSeat(@RequestBody SeatDTO seatDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = seatService.updateSeat(seatDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
