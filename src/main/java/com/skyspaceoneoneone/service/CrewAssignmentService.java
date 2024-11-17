package com.skyspaceoneoneone.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.skyspaceoneoneone.domain.CrewAssignment;
import com.skyspaceoneoneone.dto.CrewAssignmentDTO;
import com.skyspaceoneoneone.dto.CrewAssignmentSearchDTO;
import com.skyspaceoneoneone.dto.CrewAssignmentPageDTO;
import com.skyspaceoneoneone.dto.CrewAssignmentConvertCriteriaDTO;
import com.skyspaceoneoneone.service.GenericService;
import com.skyspaceoneoneone.dto.common.RequestDTO;
import com.skyspaceoneoneone.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface CrewAssignmentService extends GenericService<CrewAssignment, Integer> {

	List<CrewAssignment> findAll();

	ResultDTO addCrewAssignment(CrewAssignmentDTO crewAssignmentDTO, RequestDTO requestDTO);

	ResultDTO updateCrewAssignment(CrewAssignmentDTO crewAssignmentDTO, RequestDTO requestDTO);

    Page<CrewAssignment> getAllCrewAssignments(Pageable pageable);

    Page<CrewAssignment> getAllCrewAssignments(Specification<CrewAssignment> spec, Pageable pageable);

	ResponseEntity<CrewAssignmentPageDTO> getCrewAssignments(CrewAssignmentSearchDTO crewAssignmentSearchDTO);
	
	List<CrewAssignmentDTO> convertCrewAssignmentsToCrewAssignmentDTOs(List<CrewAssignment> crewAssignments, CrewAssignmentConvertCriteriaDTO convertCriteria);

	CrewAssignmentDTO getCrewAssignmentDTOById(Integer crewAssignmentId);







}





