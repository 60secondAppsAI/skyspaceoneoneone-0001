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

import com.skyspaceoneoneone.domain.Program;
import com.skyspaceoneoneone.dto.ProgramDTO;
import com.skyspaceoneoneone.dto.ProgramSearchDTO;
import com.skyspaceoneoneone.dto.ProgramPageDTO;
import com.skyspaceoneoneone.service.ProgramService;
import com.skyspaceoneoneone.dto.common.RequestDTO;
import com.skyspaceoneoneone.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/program")
@RestController
public class ProgramController {

	private final static Logger logger = LoggerFactory.getLogger(ProgramController.class);

	@Autowired
	ProgramService programService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Program> getAll() {

		List<Program> programs = programService.findAll();
		
		return programs;	
	}

	@GetMapping(value = "/{programId}")
	@ResponseBody
	public ProgramDTO getProgram(@PathVariable Integer programId) {
		
		return (programService.getProgramDTOById(programId));
	}

 	@RequestMapping(value = "/addProgram", method = RequestMethod.POST)
	public ResponseEntity<?> addProgram(@RequestBody ProgramDTO programDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = programService.addProgram(programDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/programs")
	public ResponseEntity<ProgramPageDTO> getPrograms(ProgramSearchDTO programSearchDTO) {
 
		return programService.getPrograms(programSearchDTO);
	}	

	@RequestMapping(value = "/updateProgram", method = RequestMethod.POST)
	public ResponseEntity<?> updateProgram(@RequestBody ProgramDTO programDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = programService.updateProgram(programDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
