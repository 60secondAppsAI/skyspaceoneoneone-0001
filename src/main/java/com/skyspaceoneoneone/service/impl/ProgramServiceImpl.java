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
import com.skyspaceoneoneone.dao.ProgramDAO;
import com.skyspaceoneoneone.domain.Program;
import com.skyspaceoneoneone.dto.ProgramDTO;
import com.skyspaceoneoneone.dto.ProgramSearchDTO;
import com.skyspaceoneoneone.dto.ProgramPageDTO;
import com.skyspaceoneoneone.dto.ProgramConvertCriteriaDTO;
import com.skyspaceoneoneone.dto.common.RequestDTO;
import com.skyspaceoneoneone.dto.common.ResultDTO;
import com.skyspaceoneoneone.service.ProgramService;
import com.skyspaceoneoneone.util.ControllerUtils;





@Service
public class ProgramServiceImpl extends GenericServiceImpl<Program, Integer> implements ProgramService {

    private final static Logger logger = LoggerFactory.getLogger(ProgramServiceImpl.class);

	@Autowired
	ProgramDAO programDao;

	


	@Override
	public GenericDAO<Program, Integer> getDAO() {
		return (GenericDAO<Program, Integer>) programDao;
	}
	
	public List<Program> findAll () {
		List<Program> programs = programDao.findAll();
		
		return programs;	
		
	}

	public ResultDTO addProgram(ProgramDTO programDTO, RequestDTO requestDTO) {

		Program program = new Program();

		program.setProgramId(programDTO.getProgramId());


		program.setName(programDTO.getName());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		program = programDao.save(program);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Program> getAllPrograms(Pageable pageable) {
		return programDao.findAll(pageable);
	}

	public Page<Program> getAllPrograms(Specification<Program> spec, Pageable pageable) {
		return programDao.findAll(spec, pageable);
	}

	public ResponseEntity<ProgramPageDTO> getPrograms(ProgramSearchDTO programSearchDTO) {
	
			Integer programId = programSearchDTO.getProgramId(); 
 			String name = programSearchDTO.getName(); 
 			String sortBy = programSearchDTO.getSortBy();
			String sortOrder = programSearchDTO.getSortOrder();
			String searchQuery = programSearchDTO.getSearchQuery();
			Integer page = programSearchDTO.getPage();
			Integer size = programSearchDTO.getSize();

	        Specification<Program> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, programId, "programId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Program> programs = this.getAllPrograms(spec, pageable);
		
		//System.out.println(String.valueOf(programs.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(programs.getTotalPages()));
		
		List<Program> programsList = programs.getContent();
		
		ProgramConvertCriteriaDTO convertCriteria = new ProgramConvertCriteriaDTO();
		List<ProgramDTO> programDTOs = this.convertProgramsToProgramDTOs(programsList,convertCriteria);
		
		ProgramPageDTO programPageDTO = new ProgramPageDTO();
		programPageDTO.setPrograms(programDTOs);
		programPageDTO.setTotalElements(programs.getTotalElements());
		return ResponseEntity.ok(programPageDTO);
	}

	public List<ProgramDTO> convertProgramsToProgramDTOs(List<Program> programs, ProgramConvertCriteriaDTO convertCriteria) {
		
		List<ProgramDTO> programDTOs = new ArrayList<ProgramDTO>();
		
		for (Program program : programs) {
			programDTOs.add(convertProgramToProgramDTO(program,convertCriteria));
		}
		
		return programDTOs;

	}
	
	public ProgramDTO convertProgramToProgramDTO(Program program, ProgramConvertCriteriaDTO convertCriteria) {
		
		ProgramDTO programDTO = new ProgramDTO();
		
		programDTO.setProgramId(program.getProgramId());

	
		programDTO.setName(program.getName());

	

		
		return programDTO;
	}

	public ResultDTO updateProgram(ProgramDTO programDTO, RequestDTO requestDTO) {
		
		Program program = programDao.getById(programDTO.getProgramId());

		program.setProgramId(ControllerUtils.setValue(program.getProgramId(), programDTO.getProgramId()));

		program.setName(ControllerUtils.setValue(program.getName(), programDTO.getName()));



        program = programDao.save(program);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public ProgramDTO getProgramDTOById(Integer programId) {
	
		Program program = programDao.getById(programId);
			
		
		ProgramConvertCriteriaDTO convertCriteria = new ProgramConvertCriteriaDTO();
		return(this.convertProgramToProgramDTO(program,convertCriteria));
	}







}
