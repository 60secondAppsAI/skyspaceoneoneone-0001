package com.skyspaceoneoneone.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.skyspaceoneoneone.domain.Program;
import com.skyspaceoneoneone.dto.ProgramDTO;
import com.skyspaceoneoneone.dto.ProgramSearchDTO;
import com.skyspaceoneoneone.dto.ProgramPageDTO;
import com.skyspaceoneoneone.dto.ProgramConvertCriteriaDTO;
import com.skyspaceoneoneone.service.GenericService;
import com.skyspaceoneoneone.dto.common.RequestDTO;
import com.skyspaceoneoneone.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface ProgramService extends GenericService<Program, Integer> {

	List<Program> findAll();

	ResultDTO addProgram(ProgramDTO programDTO, RequestDTO requestDTO);

	ResultDTO updateProgram(ProgramDTO programDTO, RequestDTO requestDTO);

    Page<Program> getAllPrograms(Pageable pageable);

    Page<Program> getAllPrograms(Specification<Program> spec, Pageable pageable);

	ResponseEntity<ProgramPageDTO> getPrograms(ProgramSearchDTO programSearchDTO);
	
	List<ProgramDTO> convertProgramsToProgramDTOs(List<Program> programs, ProgramConvertCriteriaDTO convertCriteria);

	ProgramDTO getProgramDTOById(Integer programId);







}





