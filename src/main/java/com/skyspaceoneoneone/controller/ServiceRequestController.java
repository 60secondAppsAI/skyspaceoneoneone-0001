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

import com.skyspaceoneoneone.domain.ServiceRequest;
import com.skyspaceoneoneone.dto.ServiceRequestDTO;
import com.skyspaceoneoneone.dto.ServiceRequestSearchDTO;
import com.skyspaceoneoneone.dto.ServiceRequestPageDTO;
import com.skyspaceoneoneone.service.ServiceRequestService;
import com.skyspaceoneoneone.dto.common.RequestDTO;
import com.skyspaceoneoneone.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/serviceRequest")
@RestController
public class ServiceRequestController {

	private final static Logger logger = LoggerFactory.getLogger(ServiceRequestController.class);

	@Autowired
	ServiceRequestService serviceRequestService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<ServiceRequest> getAll() {

		List<ServiceRequest> serviceRequests = serviceRequestService.findAll();
		
		return serviceRequests;	
	}

	@GetMapping(value = "/{serviceRequestId}")
	@ResponseBody
	public ServiceRequestDTO getServiceRequest(@PathVariable Integer serviceRequestId) {
		
		return (serviceRequestService.getServiceRequestDTOById(serviceRequestId));
	}

 	@RequestMapping(value = "/addServiceRequest", method = RequestMethod.POST)
	public ResponseEntity<?> addServiceRequest(@RequestBody ServiceRequestDTO serviceRequestDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = serviceRequestService.addServiceRequest(serviceRequestDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/serviceRequests")
	public ResponseEntity<ServiceRequestPageDTO> getServiceRequests(ServiceRequestSearchDTO serviceRequestSearchDTO) {
 
		return serviceRequestService.getServiceRequests(serviceRequestSearchDTO);
	}	

	@RequestMapping(value = "/updateServiceRequest", method = RequestMethod.POST)
	public ResponseEntity<?> updateServiceRequest(@RequestBody ServiceRequestDTO serviceRequestDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = serviceRequestService.updateServiceRequest(serviceRequestDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
