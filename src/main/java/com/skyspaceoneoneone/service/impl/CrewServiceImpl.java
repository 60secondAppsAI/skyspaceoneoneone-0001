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
import com.skyspaceoneoneone.dao.CrewDAO;
import com.skyspaceoneoneone.domain.Crew;
import com.skyspaceoneoneone.dto.CrewDTO;
import com.skyspaceoneoneone.dto.CrewSearchDTO;
import com.skyspaceoneoneone.dto.CrewPageDTO;
import com.skyspaceoneoneone.dto.CrewConvertCriteriaDTO;
import com.skyspaceoneoneone.dto.common.RequestDTO;
import com.skyspaceoneoneone.dto.common.ResultDTO;
import com.skyspaceoneoneone.service.CrewService;
import com.skyspaceoneoneone.util.ControllerUtils;





@Service
public class CrewServiceImpl extends GenericServiceImpl<Crew, Integer> implements CrewService {

    private final static Logger logger = LoggerFactory.getLogger(CrewServiceImpl.class);

	@Autowired
	CrewDAO crewDao;

	


	@Override
	public GenericDAO<Crew, Integer> getDAO() {
		return (GenericDAO<Crew, Integer>) crewDao;
	}
	
	public List<Crew> findAll () {
		List<Crew> crews = crewDao.findAll();
		
		return crews;	
		
	}

	public ResultDTO addCrew(CrewDTO crewDTO, RequestDTO requestDTO) {

		Crew crew = new Crew();

		crew.setCrewId(crewDTO.getCrewId());


		crew.setFirstName(crewDTO.getFirstName());


		crew.setLastName(crewDTO.getLastName());


		crew.setRole(crewDTO.getRole());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		crew = crewDao.save(crew);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Crew> getAllCrews(Pageable pageable) {
		return crewDao.findAll(pageable);
	}

	public Page<Crew> getAllCrews(Specification<Crew> spec, Pageable pageable) {
		return crewDao.findAll(spec, pageable);
	}

	public ResponseEntity<CrewPageDTO> getCrews(CrewSearchDTO crewSearchDTO) {
	
			Integer crewId = crewSearchDTO.getCrewId(); 
 			String firstName = crewSearchDTO.getFirstName(); 
 			String lastName = crewSearchDTO.getLastName(); 
 			String role = crewSearchDTO.getRole(); 
 			String sortBy = crewSearchDTO.getSortBy();
			String sortOrder = crewSearchDTO.getSortOrder();
			String searchQuery = crewSearchDTO.getSearchQuery();
			Integer page = crewSearchDTO.getPage();
			Integer size = crewSearchDTO.getSize();

	        Specification<Crew> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, crewId, "crewId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, firstName, "firstName"); 
			
			spec = ControllerUtils.andIfNecessary(spec, lastName, "lastName"); 
			
			spec = ControllerUtils.andIfNecessary(spec, role, "role"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("firstName")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("lastName")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("role")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Crew> crews = this.getAllCrews(spec, pageable);
		
		//System.out.println(String.valueOf(crews.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(crews.getTotalPages()));
		
		List<Crew> crewsList = crews.getContent();
		
		CrewConvertCriteriaDTO convertCriteria = new CrewConvertCriteriaDTO();
		List<CrewDTO> crewDTOs = this.convertCrewsToCrewDTOs(crewsList,convertCriteria);
		
		CrewPageDTO crewPageDTO = new CrewPageDTO();
		crewPageDTO.setCrews(crewDTOs);
		crewPageDTO.setTotalElements(crews.getTotalElements());
		return ResponseEntity.ok(crewPageDTO);
	}

	public List<CrewDTO> convertCrewsToCrewDTOs(List<Crew> crews, CrewConvertCriteriaDTO convertCriteria) {
		
		List<CrewDTO> crewDTOs = new ArrayList<CrewDTO>();
		
		for (Crew crew : crews) {
			crewDTOs.add(convertCrewToCrewDTO(crew,convertCriteria));
		}
		
		return crewDTOs;

	}
	
	public CrewDTO convertCrewToCrewDTO(Crew crew, CrewConvertCriteriaDTO convertCriteria) {
		
		CrewDTO crewDTO = new CrewDTO();
		
		crewDTO.setCrewId(crew.getCrewId());

	
		crewDTO.setFirstName(crew.getFirstName());

	
		crewDTO.setLastName(crew.getLastName());

	
		crewDTO.setRole(crew.getRole());

	

		
		return crewDTO;
	}

	public ResultDTO updateCrew(CrewDTO crewDTO, RequestDTO requestDTO) {
		
		Crew crew = crewDao.getById(crewDTO.getCrewId());

		crew.setCrewId(ControllerUtils.setValue(crew.getCrewId(), crewDTO.getCrewId()));

		crew.setFirstName(ControllerUtils.setValue(crew.getFirstName(), crewDTO.getFirstName()));

		crew.setLastName(ControllerUtils.setValue(crew.getLastName(), crewDTO.getLastName()));

		crew.setRole(ControllerUtils.setValue(crew.getRole(), crewDTO.getRole()));



        crew = crewDao.save(crew);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public CrewDTO getCrewDTOById(Integer crewId) {
	
		Crew crew = crewDao.getById(crewId);
			
		
		CrewConvertCriteriaDTO convertCriteria = new CrewConvertCriteriaDTO();
		return(this.convertCrewToCrewDTO(crew,convertCriteria));
	}







}
