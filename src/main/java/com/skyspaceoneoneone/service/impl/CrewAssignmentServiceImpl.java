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
import com.skyspaceoneoneone.dao.CrewAssignmentDAO;
import com.skyspaceoneoneone.domain.CrewAssignment;
import com.skyspaceoneoneone.dto.CrewAssignmentDTO;
import com.skyspaceoneoneone.dto.CrewAssignmentSearchDTO;
import com.skyspaceoneoneone.dto.CrewAssignmentPageDTO;
import com.skyspaceoneoneone.dto.CrewAssignmentConvertCriteriaDTO;
import com.skyspaceoneoneone.dto.common.RequestDTO;
import com.skyspaceoneoneone.dto.common.ResultDTO;
import com.skyspaceoneoneone.service.CrewAssignmentService;
import com.skyspaceoneoneone.util.ControllerUtils;





@Service
public class CrewAssignmentServiceImpl extends GenericServiceImpl<CrewAssignment, Integer> implements CrewAssignmentService {

    private final static Logger logger = LoggerFactory.getLogger(CrewAssignmentServiceImpl.class);

	@Autowired
	CrewAssignmentDAO crewAssignmentDao;

	


	@Override
	public GenericDAO<CrewAssignment, Integer> getDAO() {
		return (GenericDAO<CrewAssignment, Integer>) crewAssignmentDao;
	}
	
	public List<CrewAssignment> findAll () {
		List<CrewAssignment> crewAssignments = crewAssignmentDao.findAll();
		
		return crewAssignments;	
		
	}

	public ResultDTO addCrewAssignment(CrewAssignmentDTO crewAssignmentDTO, RequestDTO requestDTO) {

		CrewAssignment crewAssignment = new CrewAssignment();

		crewAssignment.setCrewAssignmentId(crewAssignmentDTO.getCrewAssignmentId());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		crewAssignment = crewAssignmentDao.save(crewAssignment);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<CrewAssignment> getAllCrewAssignments(Pageable pageable) {
		return crewAssignmentDao.findAll(pageable);
	}

	public Page<CrewAssignment> getAllCrewAssignments(Specification<CrewAssignment> spec, Pageable pageable) {
		return crewAssignmentDao.findAll(spec, pageable);
	}

	public ResponseEntity<CrewAssignmentPageDTO> getCrewAssignments(CrewAssignmentSearchDTO crewAssignmentSearchDTO) {
	
			Integer crewAssignmentId = crewAssignmentSearchDTO.getCrewAssignmentId(); 
 			String sortBy = crewAssignmentSearchDTO.getSortBy();
			String sortOrder = crewAssignmentSearchDTO.getSortOrder();
			String searchQuery = crewAssignmentSearchDTO.getSearchQuery();
			Integer page = crewAssignmentSearchDTO.getPage();
			Integer size = crewAssignmentSearchDTO.getSize();

	        Specification<CrewAssignment> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, crewAssignmentId, "crewAssignmentId"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

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

		Page<CrewAssignment> crewAssignments = this.getAllCrewAssignments(spec, pageable);
		
		//System.out.println(String.valueOf(crewAssignments.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(crewAssignments.getTotalPages()));
		
		List<CrewAssignment> crewAssignmentsList = crewAssignments.getContent();
		
		CrewAssignmentConvertCriteriaDTO convertCriteria = new CrewAssignmentConvertCriteriaDTO();
		List<CrewAssignmentDTO> crewAssignmentDTOs = this.convertCrewAssignmentsToCrewAssignmentDTOs(crewAssignmentsList,convertCriteria);
		
		CrewAssignmentPageDTO crewAssignmentPageDTO = new CrewAssignmentPageDTO();
		crewAssignmentPageDTO.setCrewAssignments(crewAssignmentDTOs);
		crewAssignmentPageDTO.setTotalElements(crewAssignments.getTotalElements());
		return ResponseEntity.ok(crewAssignmentPageDTO);
	}

	public List<CrewAssignmentDTO> convertCrewAssignmentsToCrewAssignmentDTOs(List<CrewAssignment> crewAssignments, CrewAssignmentConvertCriteriaDTO convertCriteria) {
		
		List<CrewAssignmentDTO> crewAssignmentDTOs = new ArrayList<CrewAssignmentDTO>();
		
		for (CrewAssignment crewAssignment : crewAssignments) {
			crewAssignmentDTOs.add(convertCrewAssignmentToCrewAssignmentDTO(crewAssignment,convertCriteria));
		}
		
		return crewAssignmentDTOs;

	}
	
	public CrewAssignmentDTO convertCrewAssignmentToCrewAssignmentDTO(CrewAssignment crewAssignment, CrewAssignmentConvertCriteriaDTO convertCriteria) {
		
		CrewAssignmentDTO crewAssignmentDTO = new CrewAssignmentDTO();
		
		crewAssignmentDTO.setCrewAssignmentId(crewAssignment.getCrewAssignmentId());

	

		
		return crewAssignmentDTO;
	}

	public ResultDTO updateCrewAssignment(CrewAssignmentDTO crewAssignmentDTO, RequestDTO requestDTO) {
		
		CrewAssignment crewAssignment = crewAssignmentDao.getById(crewAssignmentDTO.getCrewAssignmentId());

		crewAssignment.setCrewAssignmentId(ControllerUtils.setValue(crewAssignment.getCrewAssignmentId(), crewAssignmentDTO.getCrewAssignmentId()));



        crewAssignment = crewAssignmentDao.save(crewAssignment);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public CrewAssignmentDTO getCrewAssignmentDTOById(Integer crewAssignmentId) {
	
		CrewAssignment crewAssignment = crewAssignmentDao.getById(crewAssignmentId);
			
		
		CrewAssignmentConvertCriteriaDTO convertCriteria = new CrewAssignmentConvertCriteriaDTO();
		return(this.convertCrewAssignmentToCrewAssignmentDTO(crewAssignment,convertCriteria));
	}







}
