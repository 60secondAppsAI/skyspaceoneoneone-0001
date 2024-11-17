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
import com.skyspaceoneoneone.dao.LoungeDAO;
import com.skyspaceoneoneone.domain.Lounge;
import com.skyspaceoneoneone.dto.LoungeDTO;
import com.skyspaceoneoneone.dto.LoungeSearchDTO;
import com.skyspaceoneoneone.dto.LoungePageDTO;
import com.skyspaceoneoneone.dto.LoungeConvertCriteriaDTO;
import com.skyspaceoneoneone.dto.common.RequestDTO;
import com.skyspaceoneoneone.dto.common.ResultDTO;
import com.skyspaceoneoneone.service.LoungeService;
import com.skyspaceoneoneone.util.ControllerUtils;





@Service
public class LoungeServiceImpl extends GenericServiceImpl<Lounge, Integer> implements LoungeService {

    private final static Logger logger = LoggerFactory.getLogger(LoungeServiceImpl.class);

	@Autowired
	LoungeDAO loungeDao;

	


	@Override
	public GenericDAO<Lounge, Integer> getDAO() {
		return (GenericDAO<Lounge, Integer>) loungeDao;
	}
	
	public List<Lounge> findAll () {
		List<Lounge> lounges = loungeDao.findAll();
		
		return lounges;	
		
	}

	public ResultDTO addLounge(LoungeDTO loungeDTO, RequestDTO requestDTO) {

		Lounge lounge = new Lounge();

		lounge.setLoungeId(loungeDTO.getLoungeId());


		lounge.setName(loungeDTO.getName());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		lounge = loungeDao.save(lounge);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Lounge> getAllLounges(Pageable pageable) {
		return loungeDao.findAll(pageable);
	}

	public Page<Lounge> getAllLounges(Specification<Lounge> spec, Pageable pageable) {
		return loungeDao.findAll(spec, pageable);
	}

	public ResponseEntity<LoungePageDTO> getLounges(LoungeSearchDTO loungeSearchDTO) {
	
			Integer loungeId = loungeSearchDTO.getLoungeId(); 
 			String name = loungeSearchDTO.getName(); 
 			String sortBy = loungeSearchDTO.getSortBy();
			String sortOrder = loungeSearchDTO.getSortOrder();
			String searchQuery = loungeSearchDTO.getSearchQuery();
			Integer page = loungeSearchDTO.getPage();
			Integer size = loungeSearchDTO.getSize();

	        Specification<Lounge> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, loungeId, "loungeId"); 
			
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

		Page<Lounge> lounges = this.getAllLounges(spec, pageable);
		
		//System.out.println(String.valueOf(lounges.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(lounges.getTotalPages()));
		
		List<Lounge> loungesList = lounges.getContent();
		
		LoungeConvertCriteriaDTO convertCriteria = new LoungeConvertCriteriaDTO();
		List<LoungeDTO> loungeDTOs = this.convertLoungesToLoungeDTOs(loungesList,convertCriteria);
		
		LoungePageDTO loungePageDTO = new LoungePageDTO();
		loungePageDTO.setLounges(loungeDTOs);
		loungePageDTO.setTotalElements(lounges.getTotalElements());
		return ResponseEntity.ok(loungePageDTO);
	}

	public List<LoungeDTO> convertLoungesToLoungeDTOs(List<Lounge> lounges, LoungeConvertCriteriaDTO convertCriteria) {
		
		List<LoungeDTO> loungeDTOs = new ArrayList<LoungeDTO>();
		
		for (Lounge lounge : lounges) {
			loungeDTOs.add(convertLoungeToLoungeDTO(lounge,convertCriteria));
		}
		
		return loungeDTOs;

	}
	
	public LoungeDTO convertLoungeToLoungeDTO(Lounge lounge, LoungeConvertCriteriaDTO convertCriteria) {
		
		LoungeDTO loungeDTO = new LoungeDTO();
		
		loungeDTO.setLoungeId(lounge.getLoungeId());

	
		loungeDTO.setName(lounge.getName());

	

		
		return loungeDTO;
	}

	public ResultDTO updateLounge(LoungeDTO loungeDTO, RequestDTO requestDTO) {
		
		Lounge lounge = loungeDao.getById(loungeDTO.getLoungeId());

		lounge.setLoungeId(ControllerUtils.setValue(lounge.getLoungeId(), loungeDTO.getLoungeId()));

		lounge.setName(ControllerUtils.setValue(lounge.getName(), loungeDTO.getName()));



        lounge = loungeDao.save(lounge);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public LoungeDTO getLoungeDTOById(Integer loungeId) {
	
		Lounge lounge = loungeDao.getById(loungeId);
			
		
		LoungeConvertCriteriaDTO convertCriteria = new LoungeConvertCriteriaDTO();
		return(this.convertLoungeToLoungeDTO(lounge,convertCriteria));
	}







}
